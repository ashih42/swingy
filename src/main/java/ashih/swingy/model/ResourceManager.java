package ashih.swingy.model;

import ashih.swingy.controller.HeroSelectionController;

import java.io.File;

public class ResourceManager
{
	private static final ResourceManager instance = new ResourceManager();

	public static ResourceManager getInstance()
	{
		return (ResourceManager.instance);
	}

	private final String basePath;

	private ResourceManager()
	{
		File jarFile = new File(HeroSelectionController.class.getProtectionDomain().getCodeSource().getLocation().getPath());
		String jarPath = jarFile.toURI().toString();							// ".../target/swingy-1.0-SNAPSHOT.jar"

		while (!jarPath.endsWith("target"))
			jarPath = jarPath.substring(0, jarPath.lastIndexOf('/'));
		jarPath = jarPath.substring(0, jarPath.lastIndexOf('/') + 1);
		this.basePath = jarPath;												// ".../"
	}

	public String getBasePath()
	{
		return (this.basePath);
	}
}
