package com.nabil.maven.plugins.hudsonbuildnumber;



import java.util.Map;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.project.MavenProject;

/**
 * Echos an object string to the output screen.
 * @goal getbuildnumber
 * @requiresProject
 */


public class HudsonBuildNumberMojo extends AbstractMojo {
	/**
	 * The maven project.
	 * 
	 * @parameter expression="${project}"
	 * @required
	 * @readonly
	 */
	private MavenProject project;
	
	/**
     * hudsonUrl
     *
     * @parameter 
     * @required
     */
	
	private String hudsonUrl;
	
	/**
     * hudsonProject
     *
     * @parameter 
     * @required
     */
    private String hudsonProject;


	public void execute() throws MojoExecutionException {
		 Map map = getPluginContext();
		 String jsonHudsonUrl=hudsonUrl+"/"+hudsonProject+"/"+"api/json";
		 System.out.println(jsonHudsonUrl);
		 HudsonRestful spyRest = new HudsonRestful();
		 Integer buildNumber = spyRest.retrieveBuildNumber(jsonHudsonUrl);
		 System.out.println("Build Number : "+buildNumber);
		 project.getProperties().put( "hudsonBuildNumber", "-r" +  buildNumber);  //no build number
		 }
	
}