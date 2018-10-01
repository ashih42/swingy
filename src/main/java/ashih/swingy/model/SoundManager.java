package ashih.swingy.model;

import javafx.embed.swing.JFXPanel;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class SoundManager
{
	private static final SoundManager instance = new SoundManager();

	public static SoundManager getInstance()
	{
		return (SoundManager.instance);
	}

	private MediaPlayer bgmPlayer;
	private MediaPlayer hurtSoundPlayer;
	private final ArrayList<Media> bgmList;
	private final ArrayList<Media> deathSoundList;
	private final Media playerDeathSound;
	private final Media hurtSound;
	private final Media levelUpSound;
	private final Media equipSound;
	private final Random RNG;

	private SoundManager()
	{
		final JFXPanel fxPanel = new JFXPanel();	// necessary to initialize stuff for MediaPlayer
		this.RNG = new Random();

		this.equipSound = this.loadMedia("sounds/equip_gun.wav");
		this.levelUpSound = this.loadMedia("sounds/level_up.wav");
		this.hurtSound = this.loadMedia("sounds/minecraft_ugh.wav");
		this.playerDeathSound = this.loadMedia("sounds/wilheim_scream.wav");

		this.deathSoundList = new ArrayList<Media>();
		this.deathSoundList.add(this.loadMedia("sounds/death1.wav"));
		this.deathSoundList.add(this.loadMedia("sounds/death2.wav"));
		this.deathSoundList.add(this.loadMedia("sounds/death3.wav"));
		this.deathSoundList.add(this.loadMedia("sounds/death4.wav"));

		this.bgmList = new ArrayList<Media>();
		this.bgmList.add(this.loadMedia("bgm/ANewMorning.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Crystals.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Daisuke.mp3"));
		this.bgmList.add(this.loadMedia("bgm/DeepCover.mp3"));
		this.bgmList.add(this.loadMedia("bgm/ElectricDreams.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Flatline.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Hotline.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Hydrogen.mp3"));
		this.bgmList.add(this.loadMedia("bgm/InnerAnimal.mp3"));
		this.bgmList.add(this.loadMedia("bgm/ItsSafeNow.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Knock.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Miami2.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Musikk2.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Paris2.mp3"));
		this.bgmList.add(this.loadMedia("bgm/Perturbator.mp3"));
		this.bgmList.add(this.loadMedia("bgm/TurfMain.mp3"));

		if (this.hurtSound != null)
			this.hurtSoundPlayer = new MediaPlayer(this.hurtSound);
	}

	private Media loadMedia(String filename)
	{
		try
		{
			String pathName = ResourceManager.getInstance().getBasePath() + filename;
			Media media = new Media(pathName);
			return (media);
		}
		catch (Exception e)
		{
			System.out.println(e);
			return (null);
		}
	}

	public void playBGM()
	{
		if (this.bgmPlayer != null)
		{
			this.bgmPlayer.stop();
			this.bgmPlayer.dispose();
		}

		int index = this.RNG.nextInt(this.bgmList.size());
		Media bgm = this.bgmList.get(index);
		if (bgm != null)
		{
			this.bgmPlayer = new MediaPlayer(bgm);
			this.bgmPlayer.setVolume(0.4);
			this.bgmPlayer.setOnEndOfMedia(new Runnable()
			{
				public void run()
				{
					SoundManager.this.bgmPlayer.seek(Duration.ZERO);
				}
			});
			this.bgmPlayer.play();
		}
	}

	public void playHurtSound()
	{
		if (this.hurtSoundPlayer != null)
		{
			this.hurtSoundPlayer.stop();
			this.hurtSoundPlayer.play();
		}
	}

	private void playOneShot(Media media)
	{
		if (media != null)
		{
			final MediaPlayer tempMediaPlayer = new MediaPlayer(media);
			tempMediaPlayer.setOnEndOfMedia(new Runnable()
			{
				public void run()
				{
					tempMediaPlayer.dispose();
				}
			});
			tempMediaPlayer.play();
		}
	}

	public void playEquipSound()
	{
		this.playOneShot(this.equipSound);
	}

	public void playLevelUpSound()
	{
		this.playOneShot(this.levelUpSound);
	}

	public void playPlayerDeathSound()
	{
		this.playOneShot(this.playerDeathSound);
	}

	public void playEnemyDeathSound()
	{
		int index = this.RNG.nextInt(this.deathSoundList.size());
		Media deathSound = this.deathSoundList.get(index);
		this.playOneShot(deathSound);
	}

}
