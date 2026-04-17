#SC2002 Turn Based Combat Assignment

# Java Combat Arena
A modular, turn-based RPG engine built for SC2002. It’s built to be clean, scalable, and follows SOLID principles to the letter.

### Fresh Implementations
BCE Architecture: Strictly decoupled layers—Boundary (UI), Control (Logic), and Entity (Data)—so the code isn't a spaghetti mess.

The Archer & Poison Shot: A new hero class and DoT mechanic that proves the system is open for extension without touching the core engine.

Strategy Pattern: Turn order is handled by a swappable TurnOrderStrategy (Speed-based by default).

Factory Spawning: An EnemySpawner that manages unique IDs and difficulty scaling automatically.

Robust Status System: Fully functional engine for Stuns, Poisons, and permanent stat buffs.

### Quick Start
1.  Clone the repo.
2.  Compile and run:
    ```bash
    javac Main.java && java Main
    ```
3.  Pick a hero, set your items, and clear the waves.
