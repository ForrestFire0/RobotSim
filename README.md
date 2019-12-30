# RobotSim
Its a robot simulator! At this time, it doesnt work. But it might soon!

Step 1:
  Take from frc folder, and replace my shit code with your perfect code. Just delete my folder and paste in yours.

Step 2:
  This is the hard step. Change all of your imports to the new imports that are in the Emulator folder.
  Basically, work until intelliJ doesn't give you any errors about class imports.
  Ex: If you are using a NavX:
    Remove the import of the old NavX. (Started with edu)
    Add import for new NavX (Sample -> Emulator -> AHRS)
    
  If the import doesnt exist, you might have to make it yourself! Its pretty easy:
    Just make a class that emulates the behavior of the class you are copying. Then, either System.out.println that stuff (cuz thats easy)     or do a lot of work to put it into the Robot State class.

Step 3:
  Compile and run Sample -> Main.
  
