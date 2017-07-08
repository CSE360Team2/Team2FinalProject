package CSE360;

import java.io.*;
import java.net.*;
import java.util.*;
import org.json.*;

/*
CSE360 Summer 2017
Kyle Sun
Jingyi Li
Lin Sun
*/
// I have marked three areas with //DEBUG to indicate code to modify
// to help see all the possible messages. 

public class CompanionBrain implements Observer {
    public String message;
    public String img;
    public int x;
    public int y;
    private double friction;
    private double hspeed;
    private double vspeed;
    private double speed;
    public int frameHeight;
    public int frameWidth;
    private int state;
    private int prevState;
    private double time;
    private int tod;
    private int score;
    private int streak;
    private int correct;
    private int answered;
    private boolean hot;
    private Random r;
    private int randomizer;
    
    /** Create a CompanionBrain. The brain controls the position, message, image,
    and movement of the companion. */
    public CompanionBrain(int width, int height){
        frameWidth = width;
        frameHeight = height;
        message = "";
        img = "src/CSE360/Project2Images/ghost_station.gif";
        r = new Random();
        x = r.nextInt(frameWidth - 192) + 96;
        y = r.nextInt(frameHeight - 129) + 96;
        speed = 1;
        streak = 0;
        friction = 0;
        randomizer = r.nextInt(3) + 1;
        state = 3;
        prevState = 0;
        score = 3;
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        //DEBUG
        //Time of day
        //0 - Early, 1 - Middle, 2 - Late
        if (hour > 3 && hour <= 12) {
            tod = 0;
        } else if (hour > 12 && hour <= 19) {
            tod = 1;
        } else {
            tod = 2;
        }
        try {
            java.lang.String weaUrl = "https://api.darksky.net/forecast/29fd0065da58c853121182640d464df8/33.4255,-111.9400";
            URL url = new URL(weaUrl);
            URLConnection wc = url.openConnection();
            BufferedReader in = new BufferedReader(
            new InputStreamReader(wc.getInputStream()));
            java.lang.String inputLine;
            java.lang.String json = "";
            // Read response and add to string
            while ((inputLine = in.readLine()) != null) {
                json = json + inputLine;
            }
            in.close();
            JSONObject jsonObj = new JSONObject(json);
            double temperature = jsonObj.getJSONObject("currently").getDouble("temperature");
            //DEBUG
            if (temperature > 80) {
                hot = true;
            } else {
                hot = false;
            }
        } catch (IOException | JSONException e) {
            java.lang.System.out.println(e);
            java.lang.System.exit(1);
        }
    }
    
    /** Set the bounds of the frame in which the ghost bounces
    off of. The bounds are set to the integers passed in
    corresponding respectively to width and height.*/
    public void setFrame(int width, int height) {
        frameWidth = width;
        frameHeight = height;
    }
    
    /** Set the x and y coordinates of the ghost that
    determines its position to be displayed. Sets position
    based on current position, speed and friction.*/
    private void setPosition() {
        if (Integer.signum((int)hspeed) == -1) {
            hspeed = -speed;
            if (x < -8) {
                hspeed = Math.abs(hspeed);
            }
        } else {
            hspeed = speed;
            if ((x + 64) > frameWidth) {
                hspeed = -Math.abs(hspeed);
            }
        }
        if (Integer.signum((int)vspeed) == -1) {
            vspeed = -speed;
            if (y < -8) {
                vspeed = Math.abs(vspeed);
            }
        } else {
            vspeed = speed;
            if ((y + 64) > frameHeight) {
                vspeed = -Math.abs(vspeed);
            }
        }
        if (speed > 1) {
            speed -= friction;
        } else if (speed < -1) {
            speed += friction;
        } else if (friction != 0){
            friction = 0;
        }
        x += hspeed;
        y += vspeed;
        time++;
    }
    
    /** Fires the ghost forward in its current direction with
    the given double values for force and friction.*/
    private void shoot(double force, double friction) {
        speed = force;
        this.friction = friction;
    }
    
    /** Updates the ghost's position, movement, image and
    message based on the state. Messages and images can be randomized
    based on temperature, time of day, questions answered,
    correct/incorrect streaks, and time taken to answer.*/
    public void updateState() {
        Timer timer = new Timer();
        if (state != prevState) {
            switch (state) {
                case 1: // 1 Depressed/Furious
                if(Boolean.valueOf(choose("true", "false"))) {
                    img = "src/CSE360/Project2Images/ghost_-2.gif";
                    if (randomizer == 1) {
                        if (Boolean.valueOf(choose("true", "false"))) {
                            message = "WHY ARE YOU DOING THIS?!?!?";
                        } else {
                            message = "PLEASE!!! JUST GET A QUESTION RIGHT!!!";
                        }
                    } else if (randomizer == 2) {
                        if (hot) {
                            message = "YOU ARE GETTING HEAT STROKE! STAY STILL WHILE I CALL 911!";
                        } else {
                            message = "THE WEATHER HAS FROZEN YOUR BRAIN! SOMEONE HELP!";
                        }
                    } else {
                        if (tod == 0) {
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "GO BACK TO BED, YOU ARE DELUSIONAL!";
                            } else {
                                message = "THE EARLY BIRD GETS NOTHING RIGHT!";
                            }
                        } else if (tod == 1) {
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "BETTER HOPE YOU CAN STILL DROP THE CLASS!";
                            } else {
                                message = "YOU HAVE DISAPPOINTED ME ONCE MORE...";
                            }
                        } else {
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "PLEASE, JUST GO TO SLEEP... FOREVER...";
                            } else {
                                message = "DO YOU NEED TO TURN ON A LIGHT BECUASE IT IS DARK?";
                            }
                        }
                    }
                } else {
                    img = "src/CSE360/Project2Images/ghost_-2_2.gif";
                    switch (randomizer) {
                        case 1:
                        if (Boolean.valueOf(choose("true", "false"))) {
                            message = "AHHHHHHHHHHH!";
                        } else {
                            message = "YOU NIMCOMPOOP!";
                        }
                        break;
                        case 2:
                        if (hot) {
                            message = "IF THE WEATHER DOESN'T BURN YOU ALIVE, I WILL!";
                        } else {
                            message = "I THINK THE TEMPERATURE MATCHES YOUR IQ.";
                        }
                        break;
                        default:
                        switch (tod) {
                            case 0:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "THE SUN RISES, AND YOUR SCORE FALLS.";
                            } else {
                                message = "AT THIS POINT, YOU WILL HAVE FAILED OUT BY NOON!";
                            }   break;
                            case 1:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "YOU HAVE FAILED ME FOR THE LAST TIME, FOOL!";
                            } else {
                                message = "ALL YOUR LUNCH HAS GONE TO YOUR HEAD!";
                            }   break;
                            default:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "GO TO BED, YOU'RE LOSING YOUR HEAD!";
                            } else {
                                message = "DON'T DO QUESTIONS AFTER PARTYING LATE AT NIGHT!";
                            }   break;
                        }
                        break;
                    }
                }
                speed = 0;
                break;
                
                case 2: // 2 Sad/Angry
                if(Boolean.valueOf(choose("true", "false"))) {
                    img = "src/CSE360/Project2Images/ghost_-1.gif";
                    switch (randomizer) {
                        case 1:
                        if (Boolean.valueOf(choose("true", "false"))) {
                            message = "Don't worry, you will get the next one!";
                        } else {
                            message = "It's ok! You win some you lose some!";
                        }
                        break;
                        case 2:
                        if (hot) {
                            message = "It's alright, maybe go get some water to cool off!";
                        } else {
                            message = "Don't worry about it, maybe sure you are layering up to stay warm!";
                        }
                        break;
                        default:
                        switch (tod) {
                            case 0:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "Might be a tad early to be doing test questions!";
                            } else {
                                message = "Do not fret, the drowsiness will go away!";
                            }
                            break;
                            case 1:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "I am a tad disappointed, but keep on going!";
                            } else {
                                message = "You're going to make me cry if you get any more wrong...";
                            }
                            break;
                            default:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "Understandable performance, it is quite late in the night afterall!";
                            } else {
                                message = "Perhaps you should go to sleep? I worry about your health!";
                            }
                            break;
                        }
                        break;
                    }
                } else {
                    img = "src/CSE360/Project2Images/ghost_-1_2.gif";
                    switch (randomizer) {
                        case 1:
                        if (Boolean.valueOf(choose("true", "false"))) {
                            message = "Come on! What are you doing?";
                        } else {
                            message = "Don't do this to yourself!";
                        }
                        break;
                        case 2:
                        if (hot) {
                            message = "You're letting the heat get to your head!";
                        } else {
                            message = "Go put on a jacket or something, the weather is clearly affecting you!";
                        }
                        break;
                        default:
                        switch (tod) {
                            case 0:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "Go back to sleep and dream up some brain cells!";
                            } else {
                                message = "You are clearly not a morning person!";
                            }
                            break;
                            case 1:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "I can't believe you are doing this poorly!";
                            } else {
                                message = "Get a hold of yourself before I get a hold of you!";
                            }
                            break;
                            default:
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "If this keeps up, consider going to bed early...";
                            } else {
                                message = "It's past your bedtime. Go to sleep.";
                            }
                            break;
                        }
                        break;
                    }
                }
                speed = 1;
                break;
                
                case 3: // 3 Neutral
                img = "src/CSE360/Project2Images/ghost_station.gif";
                switch (randomizer) {
                    case 1:
                    if (Boolean.valueOf(choose("true", "false"))) {
                        message = "You can do it! I believe in you!";
                    } else {
                        message = "Let's go answer some questions!";
                    }
                    break;
                    case 2:
                    if (hot) {
                        message = "Stay " + choose("focused", "motivated", "concentrated") + ", don't let the heat get to your head!";
                    } else {
                        message = "Stay " + choose("focused", "motivated", "concentrated") + ", don't let the cold get to your bones!";
                    }
                    break;
                    default:
                    switch (tod) {
                        case 0:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "The early bird gets the worm!";
                        } else {
                            message = "Being up bright and early to tackle your problems is admirable.";
                        }
                        break;
                        case 1:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "Have you ever considered your own mortality?";
                        } else {
                            message = "...";
                        }
                        break;
                        default:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "Doing a quick brush up before heading to bed, eh?";
                        } else {
                            message = "Better finish up these questions, then go to sleep!";
                        }
                        break;
                    }
                    break;
                }
                speed = 2;
                break;
                
                case 4: // 4 Happy
                img = "src/CSE360/Project2Images/ghost_1.gif";
                switch (randomizer) {
                    case 1:
                    message = choose("Nice", "Woo", "Woohoo") + "! You are doing " + choose("it", "well", "a good job", "great") + "!";
                    break;
                    case 2:
                    if (hot) {
                        message = "Being able to think so clearly when it's so hot ... " + choose("Wow", "Amazing", "Spectacular") + "!";
                    } else {
                        message = "Even when it's so cold, you are doing so well ... " + choose("Wow", "Amazing", "Spectacular") + "!";
                    }
                    break;
                    default:
                    switch (tod) {
                        case 0:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "I guess you must be a morning person!";
                        } else {
                            message = "You must not be that drowsy! Good work so far!";
                        }
                        break;
                        case 1:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "A nice afternoon to be scoring some points!";
                        } else {
                            message = choose("Nice", "Woo", "Good job", "Good work") + "! Nice and productive in the middle of the day!";
                        }
                        break;
                        default:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "You must be used to staying up late! Keep up the " + choose("Good job", "Good work") + "!";
                        } else {
                            message = "You can go to sleep knowing that you've done well! Keep it up!";
                        }
                        break;
                    }
                    break;
                }
                speed = 3;
                break;
                
                case 5: // 5 Excited
                img = "src/CSE360/Project2Images/ghost_2.gif";
                switch (randomizer) {
                    case 1:
                    if(Boolean.valueOf(choose("true", "false"))) {
                        message = "HOLY COW! YOU'RE ON A ROLL!";
                    } else {
                        message = "UNBELIEVABLE! YOU MUST BE THE NEXT EINSTEIN!";
                    }
                    break;
                    case 2:
                    if (hot) {
                        message = "YOUR BRAIN MUST BE SOLAR POWERED! YOU'RE  "+ choose("AMAZING", "SPECTACULAR", "ON A HOT STREAK") + "!";
                    } else {
                        message = choose("NICE", "WOO") + "! THIS WEATHER MUST BE HELPING YOU KEEP A COOL HEAD!";
                    }
                    break;
                    default:
                    switch (tod) {
                        case 0:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "EARLY TO RISE, EARLY TO BIG SUCCESS!";
                        } else {
                            message = "A BRIGHT MIND SEES THE EARLY LIGHT!";
                        }
                        break;
                        case 1:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "A nice afternoon to be scoring some points~";
                        } else {
                            message = choose("Nice", "Woo", "Good job", "Good work") + "! Nice and productive in the middle of the day!";
                        }
                        break;
                        default:
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = choose("AWE-INSPIRING", "AMAZING", "SPECTACULAR", "TREMENDOUS", "INSPIRATIONAL") +" RESULTS, EVEN FOR THIS LATE AT NIGHT!";
                        } else {
                            message = "YOU MUST NOT BE AFFECTED BY DROWSINESS AT ALL! YOU ARE " + choose("AWE-INSPIRING", "AMAZING", "SPECTACULAR", "TREMENDOUS", "INSPIRATIONAL") + "!";
                        }
                        break;
                    }
                    break;
                }
                speed = 4;
                break;
                
                case 6: // 6 Correct Answer
                img = "src/CSE360/Project2Images/ghost_correct.gif";
                if (streak > 3) {
                    message = choose("You're on a roll!", "You are on a hot streak!", "WOW!" + streak + " in a row!", "Keep on going!");
                } else if (answered > 6 && answered < 9) {
                    message = choose("ALMOST DONE!", "KEEP IT UP!", "JUST A BIT LEFT!", "A FEW MORE!");
                } else if (answered == 9) {
                    message = choose("NICE, ONE MORE!", "JUST THE LAST ONE LEFT!");
                } else if (answered == 0) {
                    message = choose("GOOD START!", "NICE START!", "A GOOD LEAD!");
                } else if (time < 500) {
                    message = choose("Wow! You're a fast one!", "We've got a speedster!", "So quick!");
                } else if (time > 2000) {
                    message = choose("That one took a while!", "Slow and steady wins the race!", "A tad slow, but still correct!");
                } else {
                    message = choose("YOU DID IT!", "KEEP IT UP!", "WOW!", "CORRECT!", "WOWZA!");
                }
                shoot(20, 0.25);
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (answered == 10) {
                            state = 10;
                        } else {
                            state = score;
                        }
                    }
                }, 3000);
                break;
                
                case 7: // 7 wrong Answer
                img = "src/CSE360/Project2Images/ghost_feared.gif";
                if (streak < -2) {
                    message = choose("You are doing terrible!", "Ahh! Stop!", "You've gotten" + Math.abs(streak) + "wrong in a row...", "Slow down and think!");
                } else if (answered > 6 && answered < 9) {
                    message = choose("The torture is almost over...", "Just a few more...", "A little bit longer...", "Just the last bit left...");
                } else if (answered == 9) {
                    message = choose("Please get this over with...", "Finally, one more...");
                } else if (answered == 0) {
                    message = choose("Aaand you tripped at the starting line...", "Great start! Not.", "Failed right out the gate, eh?");
                } else if (time < 500) {
                    message = choose("Quick to failure!", "Maybe slow down a tad...", "The rabbit and the tortise, eh?");
                } else if (time > 2000) {
                    message = choose("Slow AND wrong, wow!", "I guess all that time spent wasn't on thinking...", "Can you waste more of my time please?");
                } else {
                    message = choose("Wrong!", "AHHH!", "Did you misclick?", "What the heck? Wrong!", "Go study some more...", "Can you at least try?");
                }
                timer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        if (answered == 10) {
                            state = 10;
                        } else {
                            state = score;
                        }
                    }
                }, 3000);
                speed = 0;
                break;
                
                case 8: // Spending medium time on question
                switch (randomizer) {
                    case 1:
                    if (Boolean.valueOf(choose("true", "false"))) {
                        img = "src/CSE360/Project2Images/ghost_station1.gif";
                        message = "At least I have this ball to keep me occupied...";
                    } else {
                        img = "src/CSE360/Project2Images/ghost_station3.gif";
                        message = "A good time to practice my dancing!";
                    }
                    break;
                    case 2:
                    if (hot) {
                        img = "src/CSE360/Project2Images/ghost_hot1.gif";
                        if (Boolean.valueOf(choose("true", "false"))) {
                            message = "Could you perhaps speed it up? This heat is a tad much.";
                        } else {
                            message = "This heat is unbearable... Please hurry...";
                        }
                        speed = 3;
                    } else {
                        img = "src/CSE360/Project2Images/ghost_cold1.gif";
                        if (Boolean.valueOf(choose("true", "false"))) {
                            message = "C-c-ould you h-h-hurry, it's " + choose("f-f-freezing...", "c-c-cold!", "a bit ch-chilly...");
                        } else {
                            message = "I sh-should have b-brought my jacket if I knew you'd t-take this long...";
                        }
                        speed = 1;
                    }
                    break;
                    default:
                    switch (tod) {
                        case 0:
                        img = "src/CSE360/Project2Images/ghost_drowsy.gif";
                        speed = 1;
                        if(Boolean.valueOf(choose("true", "false"))) {
                            message = "It is too early for this, I'm going back to sleep...";
                            } else {
                                message = "I just woke up, but I might as well not have...";
                            }
                            break;
                            case 1:
                            if (Boolean.valueOf(choose("true", "false"))) {
                                img = "src/CSE360/Project2Images/ghost_station5.gif";
                                message = "";
                            } else {
                                img = "src/CSE360/Project2Images/ghost_station6.gif";
                                message = "";
                            }
                            break;
                            default:
                            img = "src/CSE360/Project2Images/ghost_drowsy.gif";
                            speed = 1;
                            if(Boolean.valueOf(choose("true", "false"))) {
                                message = "It is late, I am a getting a bit tired...";
                            } else {
                                message = "Could you hurry? I'm sleepy...";
                            }
                            break;
                        }
                        break;
                    }
                    break;
                    
                    case 9: // Spending long time on question
                    switch (randomizer) {
                        case 1:
                        if (Boolean.valueOf(choose("true", "false"))) {
                            img = "src/CSE360/Project2Images/ghost_station2.gif";
                            message = "If you keep this up, I'll be a professional footballer!";
                        } else {
                            img = "src/CSE360/Project2Images/ghost_station4.gif";
                            message = "La..la..la..~~ Hurry up!";
                        }
                        break;
                        case 2:
                        if (hot) {
                            img = "src/CSE360/Project2Images/ghost_hot2.gif";
                            if (Boolean.valueOf(choose("true", "false"))) {
                                message = "AHHH, IT'S TOO HOT, HURRY!";
                            } else {
                                message = "I'M ON FIRE, THIS WEATHER IS TERRIBLE!";
                            }
                            speed = 5;
                        } else {
                            img = "src/CSE360/Project2Images/ghost_cold2.gif";
                            if (Boolean.valueOf(choose("true", "false"))) {
                                message = "H-HURRY...";
                            } else {
                                message = "...";
                            }
                            speed = 0;
                        }
                        break;
                        default:
                        if (tod == 1) {
                            if (Boolean.valueOf(choose("true", "false"))) {
                                img = "src/CSE360/Project2Images/ghost_shocked.gif";
                                message = "I'm dying of boredom... please hurry...";
                            } else {
                                img = "src/CSE360/Project2Images/ghost_station7.gif";
                                speed = 0;
                                message = "aLl t-HIs, wAiTIng..#$% S@omEthI11ng IS w%r5ong.!#()";
                            }
                        } else {
                            img = "src/CSE360/Project2Images/ghost_sleep.gif";
                            message = "Z";
                            speed = 0;
                            int rZs = r.nextInt(6) + 2;
                            for (int i = 0; i < rZs; i++) {
                                message += "z";
                            }
                            message += "...";
                        }
                        break;
                        
                        case 10: // Done
                        img = "src/CSE360/Project2Images/ghost_win.gif";
                        message = choose("WOOOOOOOOOO!", "YOU DID IT!", "MISSION COMPLETE!", "GAME OVER!");
                        shoot(50, 0.1);
                        break;
                        }
                    }
                prevState = state;
            }
            setPosition();
            //DEBUG
            // Time to wait between changing to time based states
            if (time > 600 && time % 200 == 0) {
                randomizer = r.nextInt(3) + 1;
                state = r.nextInt(2) + 8;
            } else if (time > 400) {
                state = 9;
            } else if (time > 200) {
                state = 8;
            }
        }
        
        /** Update the values needed to determine the state, including
        the number of questions correct and the number of questions
        answered.*/
        @Override
        public void update(Observable o, Object arg) {
        	System.out.println("update called at cBrain");
            int newCorrect = 0;
            int newAnswered = ((BlackBoard)o).getQuesAnswered();
            boolean[] corrArr = ((BlackBoard)o).getCorrectorNot();
            for (int i = 0; i < corrArr.length; i ++) {
                if (corrArr[i]) {
                    newCorrect++;
                }
            }
            randomizer = r.nextInt(3) + 1;
            time = 0;
            if (newCorrect > correct) {
                score++;
                if (streak < 0) {
                    streak = 0;
                }
                streak++;
                state = 6;
                if (score > 5) {
                    score = 5;
                }
            } else if (newAnswered > answered) {
                score--;
                if (streak > 0) {
                    streak = 0;
                }
                streak--;
                state = 7;
                if (score < 1) {
                    score = 1;
                }
            }
            correct = newCorrect;
            answered = newAnswered;
        }
        
        /* Return a random string from those passed in.*/
        private String choose(String... options) {
            return options[r.nextInt(options.length)];
        }
    }
