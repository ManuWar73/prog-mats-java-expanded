package progressed.world.draw;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.*;
import mindustry.graphics.*;
import mindustry.world.*;
import mindustry.world.blocks.production.GenericCrafter.*;
import mindustry.world.draw.*;
import progressed.world.blocks.crafting.AccelerationCrafter.*;

public class DrawImpact extends DrawBlock{
    public float oscMag = 3f, oscMagDec = 0.25f;
    public Color plasma1 = Color.valueOf("ffd06b"), plasma2 = Color.valueOf("ff361b");

    public TextureRegion bottom;
    public TextureRegion[] plasmaRegions = new TextureRegion[4];

    @Override
    public void load(Block block){
        bottom = Core.atlas.find(block.name + "-bottom");
        for(int i = 0; i < 4; i++){
            plasmaRegions[i] = Core.atlas.find(block.name + "-plasma-" + i, "impact-reactor-plasma-" + i);
        }
    }

    @Override
    public void draw(GenericCrafterBuild build){
        if(!(build instanceof AcceleratingCrafterBuild b)) return;

        Draw.rect(bottom, b.x, b.y);

        float warmup = b.getSpeed();
        Draw.blend(Blending.additive);
        for(int i = 0; i < plasmaRegions.length; i++){ //Haha draw code stolen from Impact Reactor
            float r = Mathf.absin(b.totalActivity, 2f + i * 1f, oscMag - i * oscMagDec);

            Draw.color(plasma1, plasma2, (float)i / (plasmaRegions.length - 1f));
            Draw.alpha((0.3f + Mathf.absin(Time.time, 2f + i * 2f, 0.3f + i * 0.05f)) * warmup);
            TextureRegion reg = plasmaRegions[i];
            Draw.rect(
                reg,
                b.x, b.y,
                reg.width * Draw.scl + r, reg.height * Draw.scl + r,
                b.totalActivity * (12 + i * 6f)
            );
        }
        Draw.blend();
        Draw.color();

        Draw.z(Layer.block + 1);
        Draw.rect(b.block.region, b.x, b.y);
    }

    @Override
    public TextureRegion[] icons(Block block){
        return new TextureRegion[]{bottom, block.region};
    }
}