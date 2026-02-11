package com.example.voiceassistant;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button btnSpeak;
    TextView txtResult;
    TextToSpeech tts;
    int REQ_CODE = 100;

    // English Common Jokes (funny, no tech jokes)
    String[] englishJokes = {
            "Why did the tomato turn red? Because it saw the salad dressing! 😄",
            "Why did the math book look sad? Because it had too many problems 🤣",
            "Why did the banana go to the doctor? Because it wasn’t peeling well 🍌",
            "Why don’t eggs tell jokes? They might crack up 😆",
            "Why did the bicycle fall over? Because it was two-tired 🚲",
            "Why did the cookie go to the doctor? It felt crummy 🍪"
    };

    // Tamil Common Jokes
    String[] tamilCommonJokes = {
            "என்ன சாப்பாடு பண்ணணும்? நான் hungry 😄",
            "பூனை ஏன் computer பார்த்தது? அது mouse பிடிக்கணும் 😹",
            "கடைமா homework பண்ணிக்கிட்டே இருக்கிறேன் 😅",
            "ஏன் rooster காலை காலை கூக்குரல் பண்ணுது? அது alarm clock 😆",
            "மரம் ஏன் சோம்பேறி? அது leaves விடாமல் இருக்கிறது 🍃",
            "பள்ளி bus ஏன் slow? அது students collect பண்ணுது 🚌",
            "மழை ஏன் happy? அது plants க்கு water கொடுக்கிறது ☔",
            "நாய் ஏன் happy? அது fetch game ஆட்டம் பண்ணுது 🐶",
            "பால் ஏன் குடிக்க முடியவில்லை? அது fridge-ல் frozen 😜",
            "கேக் ஏன் triste? அது icing இல்லாம இருக்குது 🎂"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnSpeak = findViewById(R.id.btnSpeak);
        txtResult = findViewById(R.id.txtResult);

        tts = new TextToSpeech(this, status -> {
            if(status == TextToSpeech.SUCCESS){
                tts.setLanguage(Locale.ENGLISH);
                speak("Welcome! How can I help you?");
            }
        });

        btnSpeak.setOnClickListener(v -> startListening());
    }

    void startListening(){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-US");
        startActivityForResult(intent, REQ_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==REQ_CODE && resultCode==RESULT_OK && data!=null){
            ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String command = result.get(0).toLowerCase();
            txtResult.setText(command);

            Random r = new Random();

            // Greetings & Basic Fun (Extra greetings added)
            if(command.contains("who are you")) speak("I am your personal voice assistant");
            else if(command.contains("hello")) speak("Hello! How can I help you?");
            else if(command.contains("i love you")) speak("I love you too ❤️");
            else if(command.contains("thank you")) speak("You are welcome 😊");
            else if(command.contains("good morning")) speak("Good morning! Have a great day 🌞");
            else if(command.contains("good night")) speak("Good night! Sweet dreams 🌙");
            else if(command.contains("bye")) speak("Bye! Talk to you later 👋");
            else if(command.contains("how are you")) speak("I am fine, thank you! How about you?");
            else if(command.contains("are you real")) speak("I am as real as your phone can talk 😁");
            else if(command.contains("do you love me")) speak("Of course! ❤️");
            else if(command.contains("what’s your name") || command.contains("what is your name")) speak("I am your friendly assistant");
            else if(command.contains("good afternoon")) speak("Good afternoon! Hope your day is going well ☀️");
            else if(command.contains("good evening")) speak("Good evening! How was your day? 🌇");

                // Fun Commands
            else if(command.contains("sing")) speak("La la la… I am singing for you 🎵");
            else if(command.contains("dance")) speak("I am dancing 💃🕺 …imagine me shaking!");
            else if(command.contains("cheer")) speak("Don't worry! You are amazing 😁");
            else if(command.contains("magic")) speak("Abracadabra you are amazing! ✨");
            else if(command.contains("imitate")) speak("Mimic mode activated 😜");
            else if(command.contains("clap")) speak("👏👏👏 Great job!");
            else if(command.contains("tell me something fun")) speak("Did you know? Bananas are berries, but strawberries aren’t 🍌🍓");
            else if(command.contains("riddle")) speak("I speak without a mouth and hear without ears. What am I? 🤔");

                // Jokes
            else if(command.contains("joke") && command.contains("tamil")) speak(tamilCommonJokes[r.nextInt(tamilCommonJokes.length)]);
            else if(command.contains("joke")) speak(englishJokes[r.nextInt(englishJokes.length)]);

                // Open Apps
            else if(command.contains("youtube")) { speak("Opening YouTube"); openApp("com.google.android.youtube"); }
            else if(command.contains("instagram")) { speak("Opening Instagram"); openApp("com.instagram.android"); }
            else if(command.contains("whatsapp")) { speak("Opening WhatsApp"); openApp("com.whatsapp"); }
            else if(command.contains("snapchat")) { speak("Opening Snapchat"); openApp("com.snapchat.android"); }
            else if(command.contains("chrome")) { speak("Opening Chrome"); openApp("com.android.chrome"); }
            else if(command.contains("camera")) { speak("Opening Camera"); startActivity(new Intent("android.media.action.IMAGE_CAPTURE")); }
            else if(command.contains("settings")) { speak("Opening Settings"); startActivity(new Intent(Settings.ACTION_SETTINGS)); }
            else if(command.contains("call")) { speak("Opening Dialer"); startActivity(new Intent(Intent.ACTION_DIAL)); }
            else if(command.contains("bluetooth")) { speak("Opening Bluetooth settings"); startActivity(new Intent(Settings.ACTION_BLUETOOTH_SETTINGS)); }

            else speak("Sorry, I did not understand");
        }
    }

    void openApp(String packageName){
        Intent intent = getPackageManager().getLaunchIntentForPackage(packageName);
        if(intent!=null) startActivity(intent);
        else speak("App not installed");
    }

    void speak(String text){
        tts.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy(){
        if(tts!=null){ tts.stop(); tts.shutdown(); }
        super.onDestroy();
    }
}
