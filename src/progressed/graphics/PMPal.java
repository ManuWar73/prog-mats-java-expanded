package progressed.graphics;

import arc.graphics.*;

import static arc.graphics.Color.*;

public class PMPal{
    //Single colors
    public static Color

    lightGray = valueOf("B0BAC0"),
    midGray = valueOf("989AA4"),
    darkGray = valueOf("6E7080"),
    darkBrown = valueOf("4d2c0c"),

    darkOutline = Color.valueOf("2e3142"),

    pixelFront = valueOf("FF84C1"),
    pixelBack = valueOf("EF4A9D"),

    missileBasic = valueOf("D4816B"),
    missileFrag = valueOf("9CB664"),

    apotheosisLaser = valueOf("CE5EE5"),
    apotheosisLaserDark = valueOf("9A27C4"),
    pissbeam = valueOf("e5c85e"),
    pissbeamDark = valueOf("c4b427");

    //Color sets
    public static Color[]

    apotheosisLaserColors = {Color.valueOf("9A27C455"), Color.valueOf("9A27C4aa"), apotheosisLaser, white},
    pissbeamColors = {valueOf("c4b42755"), valueOf("c4b427aa"), valueOf("e5c85e"), white};
}