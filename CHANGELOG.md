# Changelog

- The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).
- The version is based off [Forge Versioning](https://mcforge.readthedocs.io/en/latest/conventions/versioning/.)

# Notes
There have been two issues identified with loot learning and the Tartarus dimension. If Tartarus is not loaded then 
no loot learning can occur.

- Forge earlier than 1.16.4-35.0.15 does not load dimensions on servers for first run.
- Quark realistic terrain causes Tartarus not to load on dedicated servers.

## [1.16.3-1.0.2.0] - 2020-XX-YY
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
- Changed Infuser enchanted books recipes to make them unique
- Increased internal tank of the Infuser from 5000mb -> 10000mb
### Fixed
- Fix layout block changing tiers in steps of two
- Fix duplicate texture for Scale The Summit III [#465]
- Fix Mob Shard recipe missing for clearing in crafting table [#466]
- Fix heart display showing wrong cell capacity [#463]
- Fix Infuser not accepting stacks in augment slot that exceed the exact recipe requirements [#451]
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




