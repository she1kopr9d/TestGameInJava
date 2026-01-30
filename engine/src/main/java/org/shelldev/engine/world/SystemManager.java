package org.shelldev.engine.world;

import java.util.ArrayList;
import java.util.ServiceLoader;
import java.util.function.Consumer;

public class SystemManager {
    private static final ArrayList<org.shelldev.engine.world.System<?>> _registrySystems = new ArrayList();

    public static void registerSystem(org.shelldev.engine.world.System<?> system){
        _registrySystems.add(system);
    }

    public static void loadAllSystems() {
        ServiceLoader<ESystemBase> loader = ServiceLoader.load(ESystemBase.class);

        for (ESystemBase system : loader) {
            java.lang.System.out.println("Registered system: " + system.getClass().getSimpleName());
        }
    }

    public static void printRegisteredSystems() {
        java.lang.System.out.println("=== Registered Systems ===");
        for (org.shelldev.engine.world.System<?> system : _registrySystems) {
            java.lang.System.out.println("- " + system.getClass().getSimpleName());
        }
        java.lang.System.out.println("==========================");
    }

    public static void iterableSystems(Consumer<org.shelldev.engine.world.System<?>> action) {
        for (org.shelldev.engine.world.System<?> system : _registrySystems) {
            action.accept(system);
        }
    }

    public static void createSystems(){
        SystemManager.iterableSystems(system -> system.create());
    }

    public static void initSystems(){
        SystemManager.iterableSystems(system -> system.init());
    }

    public static void updateSystems(){
        SystemManager.iterableSystems(system -> system.update());
    }

    public static void renderSystems(){
        SystemManager.iterableSystems(system -> system.render());
    }

    public static void postRenderSystems(){
        SystemManager.iterableSystems(system -> system.postRender());
    }


    public static void removeSystems(){
        SystemManager.iterableSystems(system -> system.remove());
    }
}
