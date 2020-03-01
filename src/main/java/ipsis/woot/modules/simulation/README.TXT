Simulation
==========

Library
-------
Repository of the drop information on a per-mob basis.
Provides the possible drops and a rolled list of dropped items.
Custom drop information takes priority over simulated information for a specific drop

DropLibrary:
    learnSimulatedDrops - adds the provided drops to the library for the key=mob/looting
                        - also increments the simulated kills counter
    learnSimulatedDropsSilent - adds the provided drops to the library for the key=mob/looting
    getDropSummary - get a high level summary of the possible drops for the mob and the drop chance
                   - this is created from the simulated or custom drop information
    getRolledDrops - list of items that have been rolled for this key=mob/looting
    getKnownMobs - list of mobs that the library knows about for both custom and simulated

DropSummary:
    High level summary of the drops for the mob.
    Provides drop chance information for each looting level.

The library stores the drop information in the class MobData.
MobData
    simulatedKills - how many times we have simulated a kill of this mob at this looting level
    drops - list of MobDropData

MobDropData
    itemStack - the item that will drop
    simulatedDropCount - how many times, per looting level, the item dropped
    stackSizeDropCountSimulated - how many times, per looting level, the item dropped with a specific stack size
    customChanceToDrop - percentage chance, per looting level, the item dropped
    stackSizeDropChanceCustom - percentage chance, per looting level, the item dropped with a specific stack size


