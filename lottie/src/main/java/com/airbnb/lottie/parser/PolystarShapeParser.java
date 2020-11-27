package com.airbnb.lottie.parser;

import android.graphics.PointF;

import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.animatable.AnimatableFloatValue;
import com.airbnb.lottie.model.animatable.AnimatableValue;
import com.airbnb.lottie.model.content.PolystarShape;
import com.airbnb.lottie.parser.moshi.JsonReader;

import java.io.IOException;

class PolystarShapeParser {
    private static final JsonReader.Options NAMES = JsonReader.Options.of(
            "nm",
            "sy",
            "pt",
            "p",
            "r",
            "or",
            "os",
            "ir",
            "is",
            "hd"
            , "pty"
    );

    private PolystarShapeParser() {
    }

    static PolystarShape parse(
            JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        PolystarShape.Type type = null;
        AnimatableFloatValue points = null;
        AnimatableValue<PointF, PointF> position = null;
        AnimatableFloatValue rotation = null;
        AnimatableFloatValue outerRadius = null;
        AnimatableFloatValue outerRoundedness = null;
        AnimatableFloatValue innerRadius = null;
        AnimatableFloatValue innerRoundedness = null;
        boolean hidden = false;
        int pty = 0;
        while (reader.hasNext()) {
            switch (reader.selectName(NAMES)) {
                case 0: // "nm",
                    name = reader.nextString();
                    break;
                case 1: // "sy",
                    type = PolystarShape.Type.forValue(reader.nextInt());
                    break;
                case 2: //   "pt",
                    points = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 3: // "p",
                    position = AnimatablePathValueParser.parseSplitPath(reader, composition);
                    break;
                case 4: // "r",
                    rotation = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 5:// "or",
                    outerRadius = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                case 6: // "os",
                    outerRoundedness = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 7: // "ir",
                    innerRadius = AnimatableValueParser.parseFloat(reader, composition);
                    break;
                case 8: // "is",
                    innerRoundedness = AnimatableValueParser.parseFloat(reader, composition, false);
                    break;
                case 9: // "hd"
                    hidden = reader.nextBoolean();
                    break;
                case 10:
                    pty = reader.nextInt();
                    break;
                default:
                    reader.skipName();
                    reader.skipValue();
            }
        }
        PolystarShape shape = new PolystarShape(
                name, type, points, position, rotation, innerRadius, outerRadius,
                innerRoundedness, outerRoundedness, hidden);
        shape.setPty(pty);
        return shape;
    }
}
