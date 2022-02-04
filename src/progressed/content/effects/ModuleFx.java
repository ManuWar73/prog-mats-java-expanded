package progressed.content.effects;

import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import mindustry.entities.*;
import mindustry.graphics.*;
import progressed.graphics.*;

import static arc.graphics.g2d.Draw.*;
import static arc.graphics.g2d.Lines.*;
import static arc.math.Angles.*;
import static arc.util.Tmp.*;

public class ModuleFx{
    public static Effect

    irisTrail = new Effect(30f, e -> {
        color(Pal.surge, Color.white, e.fin());
        v1.trns(e.rotation + 90f, Mathf.randomSeed(e.id, -1f, 1f));
        v2.trns(e.rotation, -e.fin());

        Fill.circle(e.x + v1.x + v2.x, e.y + v1.y + v2.y, e.fout() * 2f);

        Drawf.light(e.x + v1.x + v2.x, e.y + v1.y + v2.y, e.fout() * 2f * 5f, Pal.lancerLaser, 0.6f * e.fout());
    }).layer(Layer.bullet - 0.01f),

    irisHit = new Effect(14, e -> {
        e.scaled(7f, s -> {
            color(Color.white, Pal.surge, s.fin());
            Lines.circle(e.x, e.y, s.fin() * 3f);
        });

        color(Color.white, Pal.surge, e.fin());
        stroke(0.5f + e.fout());

        randLenVectors(e.id, 4, e.fin() * 8f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() + 1f);
        });

        Drawf.light(e.x, e.y, 9f, Pal.surge, 0.6f * e.fout());
    }),

    irisDespawn = new Effect(30f, e -> {
        color(Pal.surge, Color.white, e.fin());
        v1.trns(e.rotation, -e.fin());

        Fill.circle(e.x + v1.x, e.y + v1.y, 3f * e.fout());

        e.scaled(26f, s -> {
            color(Pal.surge, Color.white, s.fin());
            stroke(s.fout() * 2f);

            randLenVectors(e.id, 6, s.fin() * 7f, (x, y) -> {
                float ang = Mathf.angle(x, y);
                lineAngle(e.x + x, e.y + y, ang, s.fout() * 2f + 1f);
            });
        });

        Drawf.light(e.x, e.y, e.fout() * 2f * 5f, Pal.surge, 0.6f * e.fout());
    }),

    flameShoot = new Effect(12f, e -> {
        color(Color.white, Pal.remove, e.fin());
        stroke(e.fout() * 0.7f + 0.3f);

        randLenVectors(e.id, 7, 25f * e.finpow(), e.rotation, 50f, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fin() * 3f + 1f);
        });
    }),

    lotusShoot = new Effect(8f, e -> {
        color(Color.white, Pal.surge, e.fin());
        float w = 1f + 4f * e.fout();
        Drawf.tri(e.x, e.y, w, 12f * e.fout(), e.rotation);
        Drawf.tri(e.x, e.y, w, 2f * e.fout(), e.rotation + 180f);
    }),

    lotusShootSmoke = new Effect(20f, e -> {
        color(Color.white, Pal.surge, e.fin());

        stroke(e.fout());
        randLenVectors(e.id, 5, e.finpow() * 5f, e.rotation, 20f, (x, y) -> {
            lineAngle(e.x + x, e.y + y, Mathf.angle(x, y), e.fout() * 1.5f);
        });
    }),

    hitLotus = new Effect(14, e -> {
        color(Color.white, Pal.surge, e.fin());

        e.scaled(7f, s -> {
            stroke(0.5f + s.fout());
            Lines.circle(e.x, e.y, s.fin() * 5f);
        });

        stroke(0.5f + e.fout());

        randLenVectors(e.id, 5, e.fin() * 15f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 3 + 1f);
        });

        Drawf.light(e.x, e.y, 20f, Pal.surge, 0.6f * e.fout());
    }),

    healCross = new Effect(180f, e -> {
        float up = e.fin() * 16f;
        color(Pal.heal, Interp.smoother.apply(e.fslope()));
        PMDrawf.plus(e.x, e.y + up, 3f, 0f);
    }),

    dissonanceDamage = new Effect(30f, e -> {
        color(e.color);
        Fill.circle(e.x, e.y, e.rotation * e.fout());
    }).layer(Layer.shields - 0.99f),

    reboundShoot = new Effect(14f, e -> {
        color(Color.white, e.color, e.fin());

        e.scaled(7f, s -> {
            stroke(0.5f + s.fout());
            Lines.circle(e.x, e.y, s.fin() * e.rotation);
        });

        stroke(0.5f + e.fout());

        randLenVectors(e.id, 5, e.fin() * e.rotation, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 3f + 1f);
        });
    }).layer(Layer.turret + 0.16f),

    jupiterCharge = new Effect(300f, e -> {
        color(Pal.lancerLaser);
        Fill.circle(e.x, e.y, e.fin(Interp.pow2Out) * 7f);

        Drawf.light(e.x, e.y, e.fin(Interp.pow2Out) * 7f * 5f, Pal.lancerLaser, 0.6f * e.fin(Interp.pow2Out));
    }).layer(Layer.bullet),

    jupiterTrail = new Effect(60f, e -> {
        color(Pal.lancerLaser, Color.white, e.fin());
        v1.trns(e.rotation + 90f, Mathf.randomSeed(e.id, -3f, 3f));
        v2.trns(e.rotation, -e.fin() * 8f);

        Fill.circle(e.x + v1.x + v2.x, e.y + v1.y + v2.y, e.fout() * 4f);

        Drawf.light(e.x + v1.x + v2.x, e.y + v1.y + v2.y, e.fout() * 4f * 5f, Pal.lancerLaser, 0.6f * e.fout());
    }).layer(Layer.bullet - 0.01f),

    jupiterHit = new Effect(14, e -> {
        e.scaled(7f, s -> {
            color(Color.white, Pal.lancerLaser, s.fin());
            Lines.circle(e.x, e.y, s.fin() * 7f);
        });

        color(Color.white, Pal.lancerLaser, e.fin());
        stroke(0.5f + e.fout());

        randLenVectors(e.id, 7, e.fin() * 20f, (x, y) -> {
            float ang = Mathf.angle(x, y);
            lineAngle(e.x + x, e.y + y, ang, e.fout() * 3 + 1f);
        });

        Drawf.light(e.x, e.y, 25f, Pal.lancerLaser, 0.6f * e.fout());
    }),

    jupiterDespawn = new Effect(60f, e -> {
        color(Pal.lancerLaser, Color.white, e.fin());
        v1.trns(e.rotation, -e.fin() * 8f);

        Fill.circle(e.x + v1.x, e.y + v1.y, 7f * e.fout());

        e.scaled(26f, s -> {
            color(Pal.lancerLaser, Color.white, s.fin());
            stroke(s.fout() * 2f);

            randLenVectors(e.id, 12, s.fin() * 14f, (x, y) -> {
                float ang = Mathf.angle(x, y);
                lineAngle(e.x + x, e.y + y, ang, s.fout() * 4 + 1f);
            });
        });

        Drawf.light(e.x, e.y, e.fout() * 7f * 5f, Pal.lancerLaser, 0.6f * e.fout());
    }),

    diffusionDamage = new Effect(14f, e -> {
        color(e.color);
        Fill.circle(e.x, e.y, e.rotation * e.fout());
    }).layer(Layer.shields - 0.99f);
}