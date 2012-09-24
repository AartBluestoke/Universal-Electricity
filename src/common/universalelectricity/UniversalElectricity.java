package universalelectricity;

import java.io.File;

import net.minecraft.src.MapColor;
import net.minecraft.src.Material;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.ForgeVersion;
import cpw.mods.fml.common.Loader;

public class UniversalElectricity
{
	public static final String VERSION = "0.8.5";
	
	public static final Configuration CONFIGURATION = new Configuration(new File(Loader.instance().getConfigDir(), "UniversalElectricity/UniversalElectricity.cfg"));
    
    //One IC2 EU is 0.012 Watt Hours. EU to Watt Hour
  	public static final float IC2_RATIO = (float)getConfigData(CONFIGURATION, "IC2 to UE Conversion Ratio", (int)(0.005f*100000))/(float)100000;
  	
  	//One MJ is 13 Watt Hours. MJ to Watt Hour
  	public static final float BC3_RATIO = (float)getConfigData(CONFIGURATION, "BC3 to UE Conversion Ratio", (int)(0.05f*100000))/(float)100000;;
  	
  	public static final float Wh_IC2_RATIO = 1/IC2_RATIO;
  	
  	public static final float Wh_BC_RATIO = 1/BC3_RATIO;
    
    /**
	 * Use this material for all your machine blocks. It can be breakable by hand.
	 */
	public static final Material machine = new Material(MapColor.ironColor);
    
    public static void forgeLock(int major, int minor, int revision, boolean strict)
    {
    	if(ForgeVersion.getMajorVersion() != major)
		{
			throw new RuntimeException("Universal Electricity: Wrong Minecraft Forge version! Get: "+major+"."+minor+"."+revision);
		}
		
    	if(ForgeVersion.getMinorVersion() < minor)
		{
			throw new RuntimeException("Universal Electricity: Minecraft Forge minor version is too old! Get: "+major+"."+minor+"."+revision);
		}
    	
    	if(ForgeVersion.getRevisionVersion() < revision)
		{
    		if(strict)
    		{
    			throw new RuntimeException("Universal Electricity: Minecraft Forge revision version is too old! Get: "+major+"."+minor+"."+revision);
    		}
    		else
    		{
    			System.out.println("Universal Electricity Warning: Minecraft Forge is not the specified version. Odd things might happen.");
    		}
		}
    }
    
    public static void forgeLock(int major, int minor, int revision)
    {
    	forgeLock(major, minor, revision, false);
    }
    
    public static int getConfigData(Configuration configuration, String name, int defaultInt)
    {
        configuration.load();
        int returnInt = defaultInt;
        returnInt = Integer.parseInt(configuration.getOrCreateIntProperty(name, Configuration.CATEGORY_GENERAL, defaultInt).value);
        configuration.save();
        return returnInt;
    }
    
    public static boolean getConfigData(Configuration configuration, String name, boolean defaultBoolean)
    {
        configuration.load();
        boolean returnBoolean = defaultBoolean;
        returnBoolean = Boolean.parseBoolean(configuration.getOrCreateBooleanProperty(name, Configuration.CATEGORY_GENERAL, defaultBoolean).value);
        configuration.save();
        return returnBoolean;
    }
    
    public static int getBlockConfigID(Configuration configuration, String name, int defaultID)
    {
        configuration.load();
        int id = defaultID;

        id = Integer.parseInt(configuration.getOrCreateBlockIdProperty(name, defaultID).value);

        if (id <= 136)
        {
            return defaultID;
        }
          
        configuration.save();
        return id;
    }
    
    public static int getItemConfigID(Configuration configuration, String name, int defaultID)
    {
        configuration.load();
        int id = defaultID;

        id = Integer.parseInt(configuration.getOrCreateIntProperty(name, Configuration.CATEGORY_ITEM, defaultID).value);

        if (id < 256)
        {
            return defaultID;
        }

        configuration.save();
        return id;
    }
}
