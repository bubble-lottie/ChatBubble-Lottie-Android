package com.airbnb.lottie.parser;


import com.airbnb.lottie.LottieComposition;
import com.airbnb.lottie.model.content.ContentModel;
import com.airbnb.lottie.model.content.ShapeGroup;
import com.airbnb.lottie.parser.moshi.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

class ShapeGroupParser {

    private ShapeGroupParser() {
    }

    private static JsonReader.Options NAMES = JsonReader.Options.of(
            "nm",
            "hd",
            "it"
            , "pty"
    );

    static ShapeGroup parse(
            JsonReader reader, LottieComposition composition) throws IOException {
        String name = null;
        boolean hidden = false;
        List<ContentModel> items = new ArrayList<>();
        int pty = 0;
        while (reader.hasNext()) {
            switch (reader.selectName(NAMES)) {
                case 0:
                    name = reader.nextString();
                    break;
                case 1:
                    hidden = reader.nextBoolean();
                    break;
                case 2:
                    reader.beginArray();
                    while (reader.hasNext()) {
                        ContentModel newItem = ContentModelParser.parse(reader, composition);
                        if (newItem != null) {
                            items.add(newItem);
                        }
                    }
                    reader.endArray();
                    break;
                case 3:
                    pty = reader.nextInt();
                    break;
                default:
                    reader.skipValue();
            }
        }

        ShapeGroup shapeGroup = new ShapeGroup(name, items, hidden);
        shapeGroup.setPty(pty);
        return shapeGroup;
    }
}
