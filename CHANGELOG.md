# Changelog

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/).

## [1.15.2-1.1.1.1] - 2020-08-01
### Added
### Changed
- Fix incorrect tier displayed in the controller tooltip [#438]
- Check MobEntity.getEntityString returning null and Woot then causing a crash [#440]
## Removed

## [1.15.2-1.1.1.0] - 2020-07-11
### Added
- Using glowstone on the Dye Liquifier to swap internal tank mode between dump/static
- Added some basic TOP support
- Updated JEI plugin to display fluids a bit more clearly and show Pure Dye recipe
### Changed
- Fix internal displaying missing block as (x,z,z) instead of (x,y,z) [#436]
- Remove all entities in the simulation cell after sweeping the drops [#419]
## Removed

## [1.15.2-1.1.0.3] - 2020-06-25
### Added
### Changed
- Fix crash when running with FTBQuests due to invalid listener casting [#434]
## Removed

## [1.15.2-1.1.0.2] - 2020-06-21
### Added
### Changed
- Added temporary fix for NPE on Evokers/Vindicators etc [#432]
  - This is based on Darkhax's fix in DarkUtilities
## Removed

## [1.15.2-1.1.0.1] - 2020-06-14
### Added
### Changed
- Fixed gui with tanks not updating [#431]
- Blacklisted Evokers due to a fatal crash [#432]
  - This is due to potion effects being applied to the FakePlayer
## Removed

## [1.15.2-1.1.0.0] - 2020-06-07
### Added
- Mod version now includes the Minecraft version
- Datapack support for custom mob ingredients both items and fluids
- Datapack support for custom mob drops
### Changed
- Fixed dual cerulean essence VAT recipe [#428]
- Fixed Anvil JEI recipe not showing controller recipe [#429]
- Override of mob specific configuration (policy.mob) has been made more user friendly
- Changed controller recipe to use SI plate not cobblestone
## Removed
- Stuff that has been removed

## [1.15.2-1.0.0.0] - 2020-05-30
### Added
- First release for 1.15.2

