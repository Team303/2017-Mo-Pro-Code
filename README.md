# 2017-Mo-Pro-Code
Code used for our 2017 FRC Steamworks robot that implements Motion Profiling (using [Pathfinder](https://github.com/JacisNonsense/Pathfinder/wiki/Pathfinder-for-FRC---Java)) in Autonomous.

An array of Trajectories is generated on the computer (quicker processing time) and then passed to the roborio using serialization and NetworkTables.

Run the RemotePathGenerator server on the host computer. Waypoints ([serialized](https://stackoverflow.com/a/448233)) and other data (maxVelocity, maxAcceleration, accelGain, etc) necessary to generate Trajectories are passed to the computer through a NetworkTables input table that goes to the RemotePathGenerator on the host computer. An array of Trajectories is generated, serialized, and tranferred back to the roborio through a NetworkTables output table where it is de-serialized. 
