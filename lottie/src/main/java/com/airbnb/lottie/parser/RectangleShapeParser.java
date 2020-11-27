package com.airbnb.lottie.parser;

import android.graphics.PointF;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatablePointValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.RectangleShape;
import com.airbnb.lottie.parser.moshi.JsonReader;

import java.io.IOException;

class RectangleShapeParser {

    private static JsonReader.Options NAMES = JsonReader.Options.of(
            "nm",
            "p",
            "s",
            "r",
            "hd"
            , "pty"
    );

    private RectangleShapeParser() {
    }

    static RectangleShape parse(
            JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        AnimatableValue<PointF, PointF> position = null;
        AnimatablePointValue size = null;
        AnimatableFloatValue roundedness = null;
        boolean hidden = false;
        int pty = 0;
        while (reader.hasNext()) {
            switch (reader.selectName(NAMES)) {
                case 0:
                    name = reader.nextString();
                    break;
                case 1:
                    position = AnimatablePathValueParser.parseSplitPath(reader, composition);
                    break;
                case 2:
                    size = AnimatableValueParser.parsePoint(reader, composition);
                    break;
                case 3:
                    roundedness = AnimatableValueParser.parseFloat(reader, composition);
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
        RectangleShape shape = new RectangleShape(name, position, size, roundedness, hidden);
        shape.setPty(pty);
        return shape;
    }
}
