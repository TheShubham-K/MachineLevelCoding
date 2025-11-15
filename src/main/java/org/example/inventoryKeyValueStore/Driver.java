package org.example.inventoryKeyValueStore;


import java.util.ArrayList;
import java.util.List;

public class Driver {

    public static void main(String[] args) {

        KeyValueStore kvStore = new KeyValueStore();

        List<Pair<String, String>> dsaBootCamp = new ArrayList<>();
        dsaBootCamp.add(new Pair<>("title", "DSA-Bootcamp"));
        dsaBootCamp.add(new Pair<>("description", "DSA-Bootcamp for Beginners"));
        dsaBootCamp.add(new Pair<>("price", "100000.00"));
        dsaBootCamp.add(new Pair<>("enrolled", "True"));
        dsaBootCamp.add(new Pair<>("estimated_time", "30"));
        kvStore.put("dsa_boot_camp", dsaBootCamp);

        System.out.println(kvStore.get("dsa_boot_camp"));
        System.out.println(kvStore.keys());
        System.out.println(kvStore.search("estimated_time", "30"));

        List<Pair<String, String>> sdeBootCamp = new ArrayList<>();
        sdeBootCamp.add(new Pair<>("title", "SDE-Bootcamp"));
        sdeBootCamp.add(new Pair<>("description", "SDE-Bootcamp for Advanced"));
        sdeBootCamp.add(new Pair<>("price", "100000.00"));
        sdeBootCamp.add(new Pair<>("enrolled", "False"));
        sdeBootCamp.add(new Pair<>("estimated_time", "3000"));
        kvStore.put("sde_boot_camp", sdeBootCamp);

        System.out.println(kvStore.search("estimated_time", "3000"));

        System.out.println("Sde boot camp deleted");
        kvStore.delete("sde_boot_camp");

        List<Pair<String, String>> sde2BootCamp = new ArrayList<>();
        sde2BootCamp.add(new Pair<>("title", "SDE-2-Bootcamp"));
        sde2BootCamp.add(new Pair<>("description", "SDE-2-Bootcamp for Pro"));
        sde2BootCamp.add(new Pair<>("price", "1000000000.00"));
        sde2BootCamp.add(new Pair<>("enrolled", "False"));
        sde2BootCamp.add(new Pair<>("estimated_time", "300"));
        kvStore.put("sde_2_boot_camp", sde2BootCamp);


        System.out.println("Key value Store after insert, update and delete");
        System.out.println(kvStore.keys());
    }

}