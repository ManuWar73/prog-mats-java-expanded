package progressed.content;

import arc.graphics.*;
import mindustry.ctype.*;
import mindustry.type.*;

public class PMItems{
    public static Item
        tenelium;

    public static void load(){
        tenelium = new Item("techtanite", Color.valueOf("B0BAC0")){{
            explosiveness = 0.1f;
            radioactivity = 1.2f;
            cost = 1.6f;
            hideDetails = false;
        }};
    }
}