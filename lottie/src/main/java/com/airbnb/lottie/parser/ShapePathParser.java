package com.airbnb.lottie.parser;


import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableShapeValue;
import com.airbnb.lottie.model.content.ShapePath;
import com.airbnb.lottie.parser.moshi.JsonReader;

import java.io.IOException;

class ShapePathParser {

    static JsonReader.Options NAMES = JsonReader.Options.of(
            "nm",
            "ind",
            "ks",
            "hd"
            , "pty"
    );

    private ShapePathParser() {
    }

    static ShapePath parse(
            JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        int ind = 0;
        AnimatableShapeValue shape = null;
        boolean hidden = false;
        int pty = 0;
        while (reader.hasNext()) {
            switch (reader.selectName(NAMES)) {
                case 0:
                    name = reader.nextString();
                    break;
                case 1:
                    ind = reader.nextInt();
                    break;
                case 2:
                    shape = AnimatableValueParser.parseShapeData(reader, composition);
                    break;
                case 3:
                    hidden = reader.nextBoolean();
                    break;
                case 4:
                    pty = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
            }
        }
        ShapePath path = new ShapePath(name, ind, shape, hidden);
        path.setPty(pty);
        return path;
    }
}
