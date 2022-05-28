package progressed.entities.units;

import arc.math.*;
import arc.struct.*;
import arc.util.*;
import mindustry.gen.*;
import mindustry.type.UnitType.*;
import progressed.content.*;
import progressed.content.effects.*;
import progressed.graphics.*;

public class DriftTrailUnit extends UnitEntity{
    public Seq<DriftTrail> driftTrails;
    static Seq<DriftEngine> tempEngines = new Seq<>();

    @Override
    public void update(){
        super.update();

        if(type.engines.contains(e -> e instanceof DriftEngine)){
            getDriftEngines();

            if(driftTrails == null){
                driftTrails = new Seq<>();
                tempEngines.each(d -> {
                    driftTrails.add(new DriftTrail(d.trailLength));
                });
            }

            for(int i = 0; i < tempEngines.size; i++){
                DriftEngine e = tempEngines.get(i);
                Tmp.v1.trns(rotation - 90f + e.rotation, e.trailVel);
                if(e.trailInherit > 0.01f){
                    Tmp.v2.set(vel).scl(e.trailInherit);
                    Tmp.v1.add(Tmp.v2);
                }
                Tmp.v2.trns(rotation - 90f, e.x ,e.y);
                driftTrails.get(i).update(
                    x + Tmp.v2.x, y + Tmp.v2.y,
                    e.trailWidth <= 0 ? e.radius + 0.25f : e.trailWidth, Tmp.v1
                );
            }
        }
    }

    @Override
    public void remove(){
        if(driftTrails != null){
            driftTrails.each(t -> UtilFx.driftTrailFade.at(x, y, 1f, type.engineColor == null ? team.color : type.engineColor, t));
        }

        super.remove();
    }

    public Seq<DriftEngine> getDriftEngines(){
        tempEngines.clear();
        type.engines.each(e -> e instanceof DriftEngine, (DriftEngine d) -> tempEngines.add(d));

        return tempEngines;
    }

    @Override
    public int classId(){
        return PMUnitTypes.classID(DriftTrailUnit.class);
    }

    public static class DriftEngine extends UnitEngine{
        public int trailLength;
        public float trailWidth, trailVel, trailInherit;

        public DriftEngine(float x, float y, float radius, float rotation){
            this.x = x;
            this.y = y;
            this.radius = radius;
            this.rotation = rotation;
        }

        public DriftEngine(){

        }

        public DriftEngine setTrail(int length, float width, float vel, float inherit){
            trailLength = length;
            trailWidth = width;
            trailVel = vel;
            trailInherit = inherit;
            return this;
        }

        public DriftEngine setTrail(int length, float vel, float inherit){
            return setTrail(length, -1, vel, inherit);
        }

        public DriftEngine copy(){
            try{
                return (DriftEngine)clone();
            }catch(CloneNotSupportedException pain){
                throw new RuntimeException("end me", pain);
            }
        }
    }
}