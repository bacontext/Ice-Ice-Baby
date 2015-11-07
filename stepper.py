import sys
import time
import RPi.GPIO as GPIO


StepDir = -2 # Set to 1 or 2 for clockwise
            # Set to -1 or -2 for anti-clockwise
WaitTime = 0.002

recipe = sys.argv[1].strip().lower()

cherry = [17, 18, 22, 27]
raspberry = [5, 6, 12, 13]
watermelon = [23, 24, 25, 16]

pinsToUse = []

if recipe == 'cherry':
  pinsToUse = [cherry]
  stopTime = 20
elif recipe == 'raspberry':
  pinsToUse = [raspberry]
  stopTime = 20
elif recipe == 'watermelon':
  pinsToUse = [watermelon]
  stopTime = 20
elif recipe == 'cherrypi':
  pinsToUse = [cherry, raspberry]
  stopTime = 15
elif recipe == 'chelon':
  pinsToUse = [cherry, watermelon]
  stopTime = 15
elif recipe == 'rasleberry':
  pinsToUse = [raspberry, watermelon]
  stopTime = 15
elif recipe == 'mh_special':
  pinsToUse = [cherry, raspberry, watermelon] 
  stopTime = 12
else:
  exit(1)

# Use BCM GPIO references
# instead of physical pin numbers
GPIO.setmode(GPIO.BCM)

# Set all first set of pins as output
for recipe in pinsToUse:
  print "Setup pins: {0}".format(recipe)
  for pin in recipe:
    GPIO.setup(pin,GPIO.OUT)
    GPIO.output(pin, False)

# Define advanced sequence
# as shown in manufacturers datasheet
Seq = [[1,0,0,0],
       [1,1,0,0],
       [0,1,0,0],
       [0,1,1,0],
       [0,0,1,0],
       [0,0,1,1],
       [0,0,0,1],
       [1,0,0,1]]
       
StepCount = len(Seq)-1

# Initialise variables
StepCounter = 0

timeRan = 0

# Start main loop
while timeRan <= stopTime:
  print "Time elapsed: {0}".format(timeRan)
  print "Time to stop: {0}".format(stopTime)

  for recipe in pinsToUse:
    for pin in range(0, 4):
      xpin = recipe[pin]
      # print StepCounter
      # print pin
      if Seq[StepCounter][pin]!=0:
        # print " Step %i Enable %i" %(StepCounter,xpin)
        GPIO.output(xpin, True)
      else:
        GPIO.output(xpin, False)

  StepCounter += StepDir

  # If we reach the end of the sequence
  # start again
  if (StepCounter>=StepCount):
    StepCounter = 0
  if (StepCounter<0):
    StepCounter = StepCount

  # Wait before moving on
  timeRan += WaitTime
  time.sleep(WaitTime)
