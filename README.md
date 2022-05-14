# MoonLanding

The code includes a simulation of the spacecraft landing on the moon when the guiding principle is as follows,
At the beginning of the landing the movement takes place around the moon in circles, when you reach the desired altitude, you change the landing angle in the other direction and increase the operation of the engine and thus the deceleration takes place.
It follows that as you approach the moon and the altitude decreases, the speed of the spacecraft increases and the engine activity increases more and more, and the vertical angle decreases and approaches zero, until the final landing is made.

## starting position

vs = 24.8 vertical speed
hs = 932 hirizenal speed
dist = Distance from the moon
ang = 58.3 The angle of the spacecraft
fuel = 121 the amount of Gaz at the begining of the simulation
NN the main engine

The purpose of the simulation run is to make a landing on the moon (when a landing is set at a distance of 0 from the moon) with a maximum amount of fuel and with a zero horizontal angle and a maximum vertical angle.
Fuel combustion calculations are performed by physical principles brought to us with the original simulation with the base code.
The guiding principle of landing is as described above.
The landing results of our simulation are six liters better than the results of the original simulation, as can be seen.

![image](https://user-images.githubusercontent.com/74323809/168447758-4cefcc0e-3ccb-4f8b-892b-c9a57e5f0a1f.png)


