package lottie2json

import java.io.File
import java.io.FileReader
import java.io.FileWriter

class MainSortTY {
    companion object {
        // 程序入口
        @JvmStatic
        fun main(args: Array<String>) {
            MainSortTY().load()
        }
    }

    private fun load() {
        val dir = File("lottie2json_dir")
        val dirTo = File("lottie2json_dir/deal")
        dirTo.mkdirs() // 创建文件夹
        dir.listFiles()?.forEachIndexed { i, file -> // 循环遍历json文件
            print("deal file:"); print(i); print(" "); println(file.name)
            if (!file.name.run { endsWith(".json", true) }) return@forEachIndexed // 不是.json后缀则不处理
            println("deal start")
            val txt = StringBuilder(FileReader(file).use { it.readText() }) // 输入流读取
            if (txt.isJson()) {
                sortToFront(txt, "ty")
                sortToFront(txt, "d")
                FileWriter(File(dirTo, file.name), false).use { it.write(txt.toString());println("deal finish:"); } // 输出流
            } else println("deal -> is no json!")
        }
        print("finish")
    }

    // 针对key进行排序
    private fun sortToFront(txt: StringBuilder, key: String) {
        val word = "\"$key\":"
        var index = 0
        while (true) {
            index = txt.indexOf(word, index)
            if (index == -1) break
            // 往前查找
            var start = index
            var sum = 0b1000 // 加速 +1 -1 计算
            while (true) {
                when (txt.elementAt(start)) {
                    '{' -> sum++
                    '}' -> sum--
                }
                if (sum == 0b1001) break // 如果sum=+1，代表有单独{，打断循环
                if (start-- ushr 31 == 1) return // 最后1位==1（即<0）则代表没有匹配的{，则文件属于不符合json格式
            }
            start++ // 下标移动到{后
            val index2 = txt.indexOf(',', index)
            val index3 = txt.indexOf('}', index)
            if (index2 < index3) {
                val all = txt.subSequence(index, index2 + 1)  // all:"key":xx,
                txt.delete(index, index2 + 1) // 删除 带后逗号
                txt.insert(start, all)
            } else if (txt.elementAt(index - 1) == ',') { // 如果前有','（不是首位key）则排序 （否则不处理）
                val all = txt.subSequence(index, index3)  // all:"key":xx ("key":xx})
                txt.delete(index - 1, index3) // 删除 带前逗号
                txt.insert(start, ',').insert(start, all)
            }
        }
    }

    // 是否为json
    private fun StringBuilder.isJson() = (startsWith('[') && endsWith(']')) || (startsWith('{') && endsWith('}'))

}