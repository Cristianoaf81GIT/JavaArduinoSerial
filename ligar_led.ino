/*
  profcristiano
*/
String entrada = "";
// the setup function runs once when you press reset or power the board
void setup() {
  // initialize digital pin LED_BUILTIN as an output.
  pinMode(LED_BUILTIN, OUTPUT);
  //serial
  Serial.begin(9600);
}

// the loop function runs over and over again forever
void loop() {
  entrada =  Serial.readString();
  if(entrada=="liga led")
  {
    Serial.println("  Led ligado!"); 
    digitalWrite(LED_BUILTIN ,HIGH);
    entrada = "";
  }else if(entrada=="desliga led"){
    Serial.println("  Led desligado!");
    digitalWrite(LED_BUILTIN ,LOW); 
    entrada = "";
  }
  
}
