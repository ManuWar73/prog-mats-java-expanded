package progressed.world.blocks.distribution.drones.stations;

import arc.*;
import arc.graphics.*;
import arc.graphics.g2d.*;
import arc.math.*;
import arc.util.io.*;
import mindustry.gen.*;
import mindustry.graphics.*;
import mindustry.type.*;
import progressed.entities.units.entity.*;
import progressed.graphics.*;

import static mindustry.Vars.*;

public class ItemDroneStation extends DroneStation{
    public float transportThreshold = 0.25f;
    public float constructTime = 60f;
    public float dumpTime = 1f;
    public int loadSize = 2;

    public TextureRegion containerFull;
    public TextureRegion[] containerRegions = new TextureRegion[2];

    public ItemDroneStation(String name){
        super(name);

        hasLiquids = false;
        hasItems = true;
        acceptsItems = true;
        selectColor = Color.yellow;
        defName = "Item";

        config(Integer.class, (ItemDroneStationBuild build, Integer i) -> {
            build.stationState = StationState.all[i];
            build.constructing = build.stationState == StationState.origin;
        });
    }

    @Override
    public void load(){
        super.load();

        containerFull = Core.atlas.find("prog-mats-item-cargo-full");
        containerRegions[0] = Core.atlas.find("prog-mats-item-cargo-base");
        containerRegions[1] = Core.atlas.find("prog-mats-item-cargo-decal");
    }

    @Override
    protected TextureRegion[] icons(){
        return new TextureRegion[]{region, containerFull};
    }

    public class ItemDroneStationBuild extends DroneStationBuild{
        public boolean constructing, open;
        public float build;

        @Override
        public void updateTile(){
            super.updateTile();

            if(timer(timerDump, dumpTime / timeScale)){
                dump();
                if(dumping && items.empty()){
                    dumping = false;
                }
            }

            if(!arrived){
                if(isOrigin()){
                    build = Mathf.approach(build, constructTime, edelta());
                    constructing = build < constructTime;
                }else if(items.empty()){
                    build = Mathf.approach(build, 0f, edelta());
                    constructing = build > 0f;
                }
            }
            open = isOrigin() ? build >= constructTime : build <= 0;
        }

        @Override
        public void loadCargo(DroneUnitEntity d){
            int[] it = new int[content.items().size];
            for(int i = 0; i < content.items().size; i++){
                it[i] = items.get(content.items().get(i));
            }
            d.cargo.load(it);
            items.clear();
            build = 0;
            constructing = true;
        }

        @Override
        public void takeCargo(DroneUnitEntity d){
            for(int i = 0; i < content.items().size; i++){
                for(int j = 0; j < d.cargo.itemCargo[i]; j++){
                    offload(content.item(i));
                }
            }
            d.cargo.empty();
        }

        @Override
        public void setLoading(DroneUnitEntity d){
            if(!arrived && !isOrigin()){
                build = constructTime;
            }
            super.setLoading(d);
        }

        @Override
        public boolean canDump(Building to, Item item){
            return (!isOrigin() || dumping) && !loading;
        }

        @Override
        public boolean ready(){
            return active || connected && open && (isOrigin() ? items.total() >= itemCapacity * transportThreshold : items.total() <= itemCapacity);
        }

        @Override
        public void offload(Item item){ //do not count as item production
            if(item == null) return;
            int dump = this.cdump;

            for(int i = 0; i < proximity.size; i++){
                incrementDump(proximity.size);
                Building other = proximity.get((i + dump) % proximity.size);
                if(other.team == team && other.acceptItem(self(), item)){
                    other.handleItem(self(), item);
                    return;
                }
            }

            handleItem(self(), item);
        }

        @Override
        public void draw(){
            super.draw();

            float progress = build / constructTime;
            if(constructing){
                Draw.draw(Layer.blockBuilding, () -> {
                    for(TextureRegion region : containerRegions){
                        PMDrawf.blockBuild(x, y, region, isOrigin() ? Pal.accent : Pal.remove, 0, progress);
                    }
                });
            }

            Draw.z(loading ? (lowFlier ? Layer.flyingUnitLow : Layer.flyingUnit) - 1 : Layer.blockOver);
            if(progress > 0.01f) Drawf.shadow(x + loadVector.x, y + loadVector.y, loadSize * tilesize * 2f, progress);
            if(build >= constructTime) Draw.rect(containerFull, x + loadVector.x, y + loadVector.y);
        }

        @Override
        public boolean acceptItem(Building source, Item item){
            return isOrigin() && items.total() + 1 <= itemCapacity && !loading && !constructing && !dumping;
        }

        @Override
        public void write(Writes write){
            super.write(write);

            write.f(build);
            write.bool(constructing);
            write.bool(open);
        }

        @Override
        public void read(Reads read, byte revision){
            super.read(read, revision);

            build = read.f();
            constructing = read.bool();
            open = read.bool();
        }
    }
}
