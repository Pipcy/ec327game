# EC327 Final Project - Group 7 - Project APEEP
---
## Summary
### Category
Animated Game

### TL;DR
Our game simulates different aspects of memorable moments from EC327, with an added twist of course!

### Description
Dive into the world of EC327 and get a taste of what it's like. Customize your character and explore the classroom. You'll be given a chance to demonstrate your machine code skills and test your knowledge of your fellow classmates and peers. Be careful when answering machine code questions as getting a question wrong might incur the wrath of Professor Trachtenberg. Compete with other players for a place in the leaderboard. 

## Authors
### Group members
Member name - Wiki Name - bu email:  
Ankita Tiwari   - AnkitaTiwari      - ankita04@bu.edu  
Pippi Pi        - PiyuPi            - ppp@bu.edu   
Elena Berrios   - ElenaBerrios      - eberrios@bu.edu  
Ethan Liang     - EthanLiang        - ethanl66@bu.edu  
Pranav Shrihari - PranavShrihari    - pranavsh@bu.edu  

### Roles
Lead: Pippi (50.0000001%), Ankita(49.9999999%)  
Front End: Pippi (56.1%), Ethan (18.69%), Pranav (18.69%), Ankita (4.66%), Elena (1.86%)  
Back End: Elena (25%), Pranav (10%), Ankita (22%), Pippi (28%), Ethan(15%)  
Documenter:	Ankita (80%), Elena (20%)  
Tester:	Elena (50%), Ethan (50%), Pranav(%)  

---
## Accomplishments

### Minimum requirements
[A list of minimum requirements that were completed entirely, one per line.]
[A list of any minimum requirements not completed or only partially completed, each with a qualitative fraction of the requirement completed and a brief explanation of what was not completed.  Penalties for partially completed requirements will be halved if the instructor agrees with the self-acknowledged status.]
Completion:  
100% - Moving graphical pieces.  
100% - The ability of the user to control some of the moving pieces through input.  
100% - Include a score that is changed based on the user satisfying requirements.  
100% - A menu item that allows the user to pause the game that the user can access at any point in the game  
100% - mini-game within the game  



### Possible features
[A list of possible features that were completed entirely, one per line, together with the percentage credit that was assigned to them.]
[A list of possible features that were only partially completed, together with a (i) the percentage credit originally assigned to them, (ii) the qualitative percentage of the feature completed, (iii) a brief explanation of what was not completed.  Penalties for partially completed requirements will be halved if the instructor agrees with the self-acknowledged status.]

Completion:  
50% - Implement a health system that is calculated/decreased based on time and player performance [5%]  
    The health system does not decrease based on time but it decreases based on player performance
    
100% - Provide a hints system that the user can access to get a text prompt on how to proceed. [5%]  
100% - Allow the user to customize some aspects of their character’s appearance. [5%]  
100% - Provide a high-score list that persists when the app is closed and then reopened. [10%]  
100% - Incorporate audio components and sound effects in the game [10%]  
100% - Gameplay mechanics that involve randomized events and calculations. [10%]  
N/A% - Allow the user to choose between light mode and dark mode and customize the placement of controls. [15%]  
    Decided we would not include this option in order to maintain consistency with the art.  
100% - Comprehensive graphical design and fine-grain animation for game components. [15%]  
100% - Incorporate online leaderboard where user can see their placement against other users. [20%]  
                                                                    Total: 77.5%  

---
## Execution

### Project source and Installation
Follow [this Google Doc](https://docs.google.com/document/d/1GzOz6lCIO9-j1esVCNZ64J18vl1_PMV1DgRqveJSo84/edit?usp=sharing) for a detailed cloning/downloading instructions!

1. After cloning the project, please select ****Pixel 2 + upsideDownCake + landscape**** as your emulator setting! (detailed instructions above)  
2. The game has great audio, so ****turning audio on**** is recommended, 
Wifi connection is required for the online leaderboard to work properly. All other parts do not. If you go to the leaderboard and nothing shows up, try going back and clicking sync data.  
3. When you play the game for the first time, you receive a ****unique ID**** number that is binded to that device. You can change your username but the score would be updated to the same ID.  
4. If you get sent back to the beginning of a game after finishing it - ****it is a glitch that we are experiencing****. Simply continue to the game and exit by hitting the pause button and “return to main menu.”  

### Usage
[Clear, terse instructions on how to use your app.  What do the different interface elements (buttons, menus, etc.) do?  How does one see the various requirements and features in action.]
Splash Screen: Contains a start button to start the game, credit page, and leaderboard.
Character Customization: Create a username, select male/female avatar, confirm button to continue. Assigns new characters a unique ID. 
MainActivity: Walk around to one of four spots in the room to trigger different activities:
1. Class Roster (Name Guessing Game)
- 5 question quiz with randomized names of students and TAs in the class. Guess whether they are real names or not.
- Pause button: Hint, resume button, return to main menu button
- Upon finishing, you are shown your score, your best score, and your average score. You can try again or continue to MainActivity. 
2. Machine Code Quiz:
- 4 machine code questions.
- Pause button: View a helpful hint, resume quiz, or return to MainActivity.
- If you get a question wrong, a minigame will spawn with chalk flying at you. Tap to jump, and dodge all the chalk. You have 3 lives. If you die, you respawn in MainActivity. If you pass, you resume the machine code quiz. 
- Results page: Shows your score and best score. You can try again or continue to MainActivity.
3. Profile: View your username, unique ID, average name guessing accuracy, best machine code quiz score, and overall score (average). 
- Button to sync your data, view online leaderboard, or return to MainActivity.
4. Art by Pippi




---
## Miscellaneous

### Extra features
[Any extra features provided by your app that were not mentioned in the minimum requirements or possible features.  Did you put your app on the Google App store?  Did you implement a half-baked idea that was not envisioned at the start of the project?]

### Challenges
[Where there any specific challenges during the implementation of this project that deserve note.  Feel free to include ideas that you tried but failed to work, in terse fashion.]

### Supporting material
Follow [this Google Doc](https://docs.google.com/document/d/1GzOz6lCIO9-j1esVCNZ64J18vl1_PMV1DgRqveJSo84/edit?usp=sharing) for a detailed cloning/downloading instructions!

### Release
We are comfortable making this public. 


### High Level Documentation for Testing
Unit tests were implemented for all applicable functions, especially those that format data to be displayed onto the screen, and those accessing and processing data through APIs. These were done through methods such as mocking. 

Interface and system tests were implemented manually through user input and demos. For example, we know that a function works when its intended result shows up on the screen.

###

Issue #7 - Personalize Gitlab has been completed
