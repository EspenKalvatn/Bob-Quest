- TiledMaps with the attribute "infinite" will not render, remember to deselect after using it in Tiled
  - How to replicate:
    - Open one of the "tiledmaps" in tiled, and make the map infinite in map properties
    - Try to load the level in the game
  - Reason:
    - Unknown
  - Status
    - Still a bug


- You can stick to wall when walking into it
  - How to replicate:
      - Walk next to any wall
      - Jump
      - While in the air, press the button to move towards the wall
      - You are stuck to the wall as long as you hold towards the wall
  - Reason:
    - You are able to walk into a wall while in the air, so the player gains speed towards the wall which lets them stick to the wall
  - Status:
    - Fixed
    - Check if there is a side collision before letting you move towards a wall.


- Unable to move away from wall when jumping next to a wall
  - How to replicate:
    - Walk next to any wall
    - Jump
    - While in the air, press the button to move the opposite direction from the wall
    - You are unable to move in that direction when next to the wall
  - Reason:   
    - Player doesn't differentiate between right and left collision
  - Status:
    - Fixed
    - Make a left collision and right collision to differentiate between left and right collision so that you can walk left when there is a right collision


- Unable to jump after sliding down a slope
  - How to replicate:
    - Stand on flat ground
    - See if you can jump
    - Slide down slope
    - See if you can jump
    - Walk into the corner between the slope and the ground while holding jump to regain the ability to jump
  - Reason:
    - Collision doesn't update when going from the slope to ground
    - This is the same reason why you can keep walking into a goomba without taking damage
  - Status:
    - Still a bug


- Game crashes when trying to reload game
  - How to replicate:
    - Jump off the cliff to restart
    - Game crashes
  - Reason
    - Game deletes and remakes all objects when restarting
    - All objects have bodies, but the bodies aren't deleted when you delete the objects
    - When something tries to interact with the body, it's unable to affect the object since it doesn't exist anymore
  - Status:
    - Fixed
    - Feleted all the bodies in the world when reloading the level.


- 