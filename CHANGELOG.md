# Changelog

- The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).
- The version is based off [Forge Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/.)

# Notes
There have been two issues identified with loot learning and the Tartarus dimension. If Tartarus is not loaded then 
no loot learning can occur.

- Forge earlier than 1.16.4-35.0.15 does not load dimensions on servers for first run.
- Quark realistic terrain causes Tartarus not to load on dedicated servers.

## [1.16.5-1.0.6.1] - 2021-??-??
### Added
- Added energy display in JEI recipes rather than blank gui space
- Added mob to factory mapping information. This has always been here, it 
  wasn't just added because someone got reprimanded by DireWolf20..... [#522]
- Added Quark-Dragon Scale (100% for 1), Baubly-Green Heart (100% for 1), Tetra-Dragon Sinew (100% for 2) to the 
  Ender Dragon drops. These match the original mod drop rates. [#518]
### Changed
- Slight bump to machine processing speed (20->40RF/tick so recipes finshed faster)
### Fixed
- Correct custom drops not allowing stack size > 1 [#517]
- Blacklisted all RFTools Dimension dimlets [#520]
### Removed

## [1.16.5-1.0.6.0] - 2021-04-25
### Added
- Added Ritual Of The Martyr/Flayed Perk for Blood Magic support [#514]
### Changed
- Added Ether Gas Wither requirement information to tooltip and guide [#516]
### Fixed
### Removed

## [1.16.5-1.0.5.0] - 2021-04-03
### Added
- Added Ether Gas perk for Industrial Foregoing when using Withers [#513]
### Changed
### Fixed
### Removed

## [1.16.5-1.0.4.2] - 2021-03-20
### Added
- Can now clear machines in crafting grid
### Changed
### Fixed
- Fix Dye Liquifier becoming stalled with pure dye tank full and all internal tanks full.
  Emptying the pure dye tank did not allow any new fluid to be generated as no items could be processed
  which triggers the pure dye generation.
- Fix Infuser still processing when no space in output slot [#512]
### Removed

## [1.16.5-1.0.4.1] - 2021-03-05
### Added
### Changed
### Fixed
- Fix world tick processing all blocks, regardless if they were in the world ie. not forming in compact machines
### Removed

## [1.16.4-1.0.4.0] - 2021-03-05
### Added
### Changed
- Updated to Forge 1.16.5
### Fixed
- Fixed empty drop slots in the heart gui [#505]
- Fixed invalid use of getStackInSlot for consuming from external storage [#493]
### Removed

## [1.16.4-1.0.3.2] - 2021-02-13
### Added
### Changed
### Fixed
- Fixed guide book showing wrong recipe for secondary controller base [#496]
- Multiblock tracker operates only on loaded blocks [#460]
- Fixed health/xp cache additions returning unknown mob xp/health [#497]
### Removed

## [1.16.4-1.0.3.1] - 2021-01-30
### Added
- New guide page for ingredients [#491]
- Update Pressing Engagement and Tumbling Doll perks to indicate need of output tanks beside Loot Exporter [#489]
- Added JEI recipe catalyst registration - (by Parker8283) [#487]
### Changed
- Bumped Exotic drop from 1% to 15% as they were so rare it felt like Destiny 2. Weights remain the same as before [#492]
- Cap the maximum enchant in the Enchant Liquifier so every item can be processed [#486]
- Disable some debug entries as it makes the debug output too noisy wrt to loot generation
### Fixed
- Fix Dye Liquifier tooltip for white [#482]
- Fix Heart consuming blocks manually placed against it when not formed [#490]
- Multiblock tracker now used synchronized lists [#460]
### Removed

## [1.16.4-1.0.3.0] - 2021-01-01
### Added
- Add advancements to give some path through the mod
- Visual indication in Heart GUI when perk tier has been capped due to low factory tier [#478]
### Changed
- Heart display now shows unique items (ie. two creeper controllers only shown gunpowder once)
### Fixed
- When capturing a charged creeper ensure that loot learning uses the charged variant [#472]
  - Note Vanilla only drops gunpowder when killed by a player
  - Records and Creeper heads are obtained through non-player kills
  - However this change will mean that mod added charged creeper drops can be learned
- Fixed the 1.16.3-1.0.2.2 changelog reporting fixes as additions ....
- Fixed Injection Press not checking for correct amount of fluid [#477]
- Fixed duplicate texture for plate and dye die
- Fix primary base using dye casing and dye plate - should only use dye plates
- Fix tier shard JEI info to state Scale The Summit perk generates them [#474]
### Removed

## [1.16.4-1.0.2.2] - 2020-12-12
### Added
### Changed
### Fixed
- Fix incorrect perk texture displayed when applied to factory [#471]
  - Tumbling Doll Of Flesh and Pressing Engagement
- Fix incorrect recipe for Tumbling Doll and Pressing Engagement [#471]
  - These recipes were both creating other perks
### Removed

## [1.16.4-1.0.2.1] - 2020-12-06
### Added
- Add fluid ingredients
- Add sounds for success and failure of block placement by Intern
- Updated Heart display to show recipe ingredients on gui rather than tooltip
- Added exotic block
  - Found only in end loot chests 
  - See factory.exotic for custom configuration
  - See exotic_drops.json for custom configuration
  - default is 1% drop chance weights lil-4,arn-4,zom-2,paz-1,lat-1
- Updated Woot guide
- Added command permission level configuration
- Updated TOP display for factory blocks
- Added support for Hwyla
  - Config option to enable/disable layout guide block counts
- Added headless perk to generate skull drops
  - See policy.mob.perk.headlessSkulls for custom configuration
- Add support for Industrial Foregoing
  - Pressing Engagement perk generates essence
  - Tumbling Doll Of Flesh perk generates pink slime and liquid meat
### Changed
- Moved guide book into datagen
- Blacklisted Ice & Fire Myrmex mobs as they are incompatible with Woot
- Changed Injection Press enchanted books recipes to make them unique
- Increased internal tank of the Injection Press from 5000mb -> 10000mb
### Fixed
- Fix layout block changing tiers in steps of two
- Fix duplicate texture for Scale The Summit III [#465]
- Fix Mob Shard recipe missing for clearing in crafting table [#466]
- Fix heart display showing wrong cell capacity [#463]
- Fix Injection Press not accepting stacks in augment slot that exceed the exact recipe requirements [#451]
- Fix Injection Press creating enchanted books with Enchantments NBT instead of StoredEnchantments NBT [#470]
### Removed

## [1.16.3-1.0.1.0] - 2020-11-22
### Added
- Heart has larger loot display pool [#456]
### Changed
- Changed how Tartarus is force loaded on startup
  - Now performs a lookup of the expected dimension.
### Fixed
- Added protection in layout tesr for layout block being air after broken [#461]
  - CTD when breaking layout block (Cannot get property DirectionProperty)
### Removed

## [1.16.3-1.0.0.2] - 2020-11-16
### Added
### Changed
### Fixed
- Added check for health/xp cache miss [#459]
### Removed
- Change MultiBlockTracker to notify after list operations [#460]

## [1.16.3-1.0.0.1] - 2020-10-31
### Added
### Changed
### Fixed
- Fixed divide by zero possibility in gui rendering [#457]
### Removed

## [1.16.3-1.0.0.0] - 2020-10-24
### Added
- First release for 1.16.3
- Add vanilla sheep wool drops. 
  - This gives an equal chance to drop each color.
  - Custom recipe file for sheep wool drops is for display only.
  - Custom recipe file for sheep can still be used to provide non-wool drops.
### Changed
- Default drop rates for Byzantium now increase per factory tier
- Fixed gui text alignment for inventory
- Fixed blocks placed against Layout Guide vanishing from users hand. Layout Guide now needs an empty hand to change tiers and layers.
### Removed
### Fixed




