package cn.cong.lottietest

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.provider.OpenableColumns
import android.support.v7.app.AppCompatActivity
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {

    private var file: File? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            requestPermissions(arrayOf(Manifest.permission_group.STORAGE), 1)
        }

        intent?.data?.let { dealFileFrom(it) }

        seek.setOnSeekBarChangeListener(SeekListener(tv, lov))
        seek.max = 20
        lov.setBackgroundResource(android.R.color.holo_green_light)
        seek.progress = 0
        lov.setSizeChangeListener { dealLoadAnim() }
        tv.minEms = 4 // 最小宽度
        bt.setOnClickListener {
            val intent = Intent(Intent.ACTION_GET_CONTENT)
            intent.type = "*/*" //设置类型，我这里是任意类型，任意后缀的可以这样写。
            intent.addCategory(Intent.CATEGORY_OPENABLE)
            startActivityForResult(intent, 1)
        }
    }

    private fun dealLoadAnim() {
        if (file == null) lov.setAnimation("狐狸气泡左.json") else lov.setAnimation(FileInputStream(file), null)
        lov.playAnimation()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1 && resultCode == RESULT_OK) { //是否选择，没选择就不会继续
            val uri = data?.data ?: return //得到uri，后面就是将uri转化成file的过程。
//            Log.d("TAG", "onActivityResult: $uri")
            dealFileFrom(uri)
        }

    }

    private fun dealFileFrom(uri: Uri) {
//        Log.d("TAG", "dealFileFrom: $uri")
        contentResolver.query(uri, arrayOf(MediaStore.Images.Media.DATA, OpenableColumns.DISPLAY_NAME), null, null, null)?.use { cs ->
            if (cs.moveToNext()) {
                val path = cs.getString(cs.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
                val name = cs.getString(cs.getColumnIndexOrThrow(OpenableColumns.DISPLAY_NAME))
//                Log.d("TAG", "dealFileFrom: $name : $path")
                if (path == null) contentResolver.openInputStream(uri)?.use { fis ->
                    val f = File(cacheDir, name ?: "${System.currentTimeMillis()}")
                    FileOutputStream(f).use { fos ->
                        fos.write(fis.readBytes())
                        file = f
                        dealLoadAnim()
                    }
                }
                else {
                    file = File(path)
                    Toast.makeText(this@MainActivity, "文件：${file?.name}", Toast.LENGTH_SHORT).show()
                    dealLoadAnim()
                }
            }
        }
    }

    class SeekListener(private val tv: TextView, private val lov: LottieAnimationView) : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
//            if (!fromUser) return
            tv.text = StringBuilder().apply { for (i in 0 until progress) append("这是一段测试文字\t").append(i).append("\t") }
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {
        }

        override fun onStopTrackingTouch(seekBar: SeekBar?) {
        }
    }
}
