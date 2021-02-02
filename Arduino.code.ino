#include <Servo.h>. 

const int trigPin = 10;
const int echoPin = 11;

long duration;
int distance;
Servo myServo;
void setup() {
  pinMode(trigPin, OUTPUT); 
  pinMode(echoPin, INPUT);
  Serial.begin(9600);
  
  myServo.attach(12);
}
void loop() {

  for(int i = 1;i<=179;i++){  
  myServo.write(i); 
    digitalWrite(trigPin, LOW); 
    delayMicroseconds(2);
    digitalWrite(trigPin, HIGH); 
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);
    duration = pulseIn(echoPin, HIGH); 
    distance= duration*0.0343/2;
    Serial.println(distance);
    Serial.println(","); 
    Serial.println(i);
    Serial.println(".");
  }
  
  for(int i = 179; i > 0; i--){  
    myServo.write(i);
    digitalWrite(trigPin, LOW); 
    delayMicroseconds(2);
    digitalWrite(trigPin, HIGH); 
    delayMicroseconds(10);
    digitalWrite(trigPin, LOW);
    duration = pulseIn(echoPin, HIGH); 
    distance= duration*0.0343/2;
    Serial.println(distance);
    Serial.println(","); 
    Serial.println(i);
    Serial.println(".");
  }
}
