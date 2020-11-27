package com.airbnb.lottie.parser;

import android.graphics.PointF;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.CircleShape;
import com.airbnb.lottie.parser.moshi.JsonReader;

import java.io.IOException;

class CircleShapeParser {

    private static JsonReader.Options NAMES = JsonReader.Options.of(
            "nm",
            "p",
            "s",
            "hd",
            "d"
            , "pty"
    );

    private CircleShapeParser() {
    }

    static CircleShape parse(
            JsonReader reader, LottieComposition composition, int d) throws IOException {
        String name = null;
        AnimatableValue<PointF, PointF> position = null;
        AnimatablePointValue size = null;
        boolean reversed = d == 3;
        boolean hidden = false;
        int pty = 0;
        while (reader.hasNext()) {
            switch (reader.selectName(NAMES)) {
                case 0: //  "nm",
                    name = reader.nextString();
                    break;
                case 1: // "p",
                    position = AnimatablePathValueParser.parseSplitPath(reader, composition);
                    break;
                case 2: //  "s",
                    size = AnimatableValueParser.parsePoint(reader, composition);
                    break;
                case 3: // "hd",
                    hidden = reader.nextBoolean();
                    break;
                case 4: // "d"
                    // "d" is 2 for normal and 3 for reversed.
                    reversed = reader.nextInt() == 3;
                    break;
                case 5:
                    pty = reader.nextInt();
                    break;
                default:
                    reader.skipName();
                    reader.skipValue();
            }
        }
        CircleShape shape = new CircleShape(name, position, size, reversed, hidden);
        shape.setPty(pty);
        return shape;
    }
}
