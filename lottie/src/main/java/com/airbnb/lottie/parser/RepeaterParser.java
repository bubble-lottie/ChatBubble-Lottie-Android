package com.airbnb.lottie.parser;


import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableTransform;
import com.airbnb.lottie.model.content.Repeater;
import com.airbnb.lottie.parser.moshi.JsonReader;

import java.io.IOException;

class RepeaterParser {

    private static JsonReader.Options NAMES = JsonReader.Options.of(
            "nm",
            "c",
            "o",
            "tr",
            "hd"
            , "pty"
    );

    private RepeaterParser() {
    }

    static Repeater parse(
            JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        AnimatableFloatValue copies = null;
        AnimatableFloatValue offset = null;
        AnimatableTransform transform = null;
        boolean hidden = false;
        int pty = 0;
        while (reader.hasNext()) {
            switch (reader.selectName(NAMES)) {
                case 0:
                    name = reader.nextString();
                    break;
                case 1:
                    copies = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 2:
                    offset = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 3:
                    transform = AnimatableTransformParser.parse(reader, composition);
                    break;
                case 4:
                    hidden = reader.nextBoolean();
                    break;
                case 5:
                    pty = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
            }
        }
        Repeater repeater = new Repeater(name, copies, offset, transform, hidden);
        repeater.setPty(pty);
        return repeater;
    }
}
