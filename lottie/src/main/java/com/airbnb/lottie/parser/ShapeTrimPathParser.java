package com.airbnb.lottie.parser;


import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.content.ShapeTrimPath;
import com.airbnb.lottie.parser.moshi.JsonReader;

import java.io.IOException;

import static com.airbnb.lottie.parser.moshi.JsonReader.Options;

class ShapeTrimPathParser {

    private ShapeTrimPathParser() {
    }

    private static Options NAMES = Options.of(
            "s",
            "e",
            "o",
            "nm",
            "m",
            "hd"
            , "pty"
    );

    static ShapeTrimPath parse(
            JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        ShapeTrimPath.Type type = null;
        AnimatableFloatValue start = null;
        AnimatableFloatValue end = null;
        AnimatableFloatValue offset = null;
        boolean hidden = false;
        int pty = 0;
        while (reader.hasNext()) {
            switch (reader.selectName(NAMES)) {
                case 0:
                    start = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 1:
                    end = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 2:
                    offset = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 3:
                    name = reader.nextString();
                    break;
                case 4:
                    type = ShapeTrimPath.Type.forId(reader.nextInt());
                    break;
                case 5:
                    hidden = reader.nextBoolean();
                    break;
                case 6:
                    pty = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
            }
        }
        ShapeTrimPath path = new ShapeTrimPath(name, type, start, end, offset, hidden);
        path.setPty(pty);
        return path;
    }
}
