package mobi.shad.s3lib;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.backends.lwjgl.LwjglFiles;
import com.badlogic.gdx.backends.lwjgl.LwjglPreferences;
import com.badlogic.gdx.files.FileHandle;
import mobi.shad.s3lib.main.S3;
import mobi.shad.s3lib.main.S3App;
import mobi.shad.s3libTest.RunTest;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;

import static mobi.shad.s3lib.main.S3.currentDir;

public class S3libRunTest extends JFrame{

	final Preferences prefs;

	public S3libRunTest() throws HeadlessException{
		super("s3Lib Test");
		prefs = new LwjglPreferences(new FileHandle(new LwjglFiles().getExternalStoragePath()+ ".prefs/s3lib-tests"));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setContentPane(new TestList());
		pack();
		setSize(getWidth(), 600);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	class TestList extends JPanel{

		public TestList(){

			BorderLayout bl = new BorderLayout();
			setLayout(bl);

			final JButton button = new JButton("Run Test");

			final JList<String> listRes = new JList<String>(new String[]{"480x320", "640x480",
																		 "800x480", "854x480", "1024x600", "1024x768", "1280x720",
																		 "1280x756", "1280x800", "1920x1080", "320x480", "480x640", "480x800",
																		 "480x854", "720x1280", "756x1280", "1080x1920"});
			JScrollPane paneRes = new JScrollPane(listRes);

			DefaultListSelectionModel mRes = new DefaultListSelectionModel();
			mRes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			mRes.setLeadAnchorNotificationEnabled(false);
			listRes.setSelectionModel(mRes);

			listRes.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent event){
					if (event.getClickCount() == 2){
						button.doClick();
					}
				}
			});

			listRes.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e){
					if (e.getKeyCode() == KeyEvent.VK_ENTER){
						button.doClick();
					}
				}
			});

			final JList<String> list = new JList<String>(RunTest.getNames());
			JScrollPane pane = new JScrollPane(list);

			DefaultListSelectionModel m = new DefaultListSelectionModel();
			m.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
			m.setLeadAnchorNotificationEnabled(false);
			list.setSelectionModel(m);

			list.addMouseListener(new MouseAdapter(){
				public void mouseClicked(MouseEvent event){
					if (event.getClickCount() == 2){
						button.doClick();
					}
				}
			});

			list.addKeyListener(new KeyAdapter(){
				public void keyPressed(KeyEvent e){
					if (e.getKeyCode() == KeyEvent.VK_ENTER){
						button.doClick();
					}
				}
			});

			list.setSelectedValue(prefs.getString("last", null), true);
			listRes.setSelectedValue(prefs.getString("lastRes", null), true);

			button.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent e){
					String testName = list.getSelectedValue();
					String resName = listRes.getSelectedValue();
					prefs.putString("last", testName);
					prefs.putString("lastRes", resName);
					prefs.flush();
					dispose();
					runTest(testName, resName);
				}
			});

			add(pane, BorderLayout.WEST);
			add(paneRes, BorderLayout.EAST);
			add(button, BorderLayout.SOUTH);
		}
	}

	public static boolean runTest(String testName, String listRes){

		S3App test = RunTest.getApplication(testName);
		if (test == null){
			return false;
		}
		try {
			currentDir = new File(".").getCanonicalPath();
		} catch (IOException e){
			e.printStackTrace();
		}

		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		if (listRes.equalsIgnoreCase("480x320")){
			config.width = 480;
			config.height = 320;
		} else if (listRes.equalsIgnoreCase("640x480")){
			config.width = 640;
			config.height = 480;
		} else if (listRes.equalsIgnoreCase("800x480")){
			config.width = 800;
			config.height = 480;
		} else if (listRes.equalsIgnoreCase("854x480")){
			config.width = 854;
			config.height = 480;
		} else if (listRes.equalsIgnoreCase("1024x600")){
			config.width = 1024;
			config.height = 600;
		} else if (listRes.equalsIgnoreCase("1024x768")){
			config.width = 1024;
			config.height = 768;
		} else if (listRes.equalsIgnoreCase("1280x720")){
			config.width = 1280;
			config.height = 720;
		} else if (listRes.equalsIgnoreCase("1280x756")){
			config.width = 1280;
			config.height = 756;
		} else if (listRes.equalsIgnoreCase("1280x800")){
			config.width = 1280;
			config.height = 800;
		} else if (listRes.equalsIgnoreCase("1920x1080")){
			config.width = 1920;
			config.height = 1080;
		} else if (listRes.equalsIgnoreCase("320x480")){
			config.width = 320;
			config.height = 480;
		} else if (listRes.equalsIgnoreCase("480x640")){
			config.width = 480;
			config.height = 640;
		} else if (listRes.equalsIgnoreCase("480x800")){
			config.width = 480;
			config.height = 800;
		} else if (listRes.equalsIgnoreCase("480x854")){
			config.width = 480;
			config.height = 854;
		} else if (listRes.equalsIgnoreCase("720x1280")){
			config.width = 720;
			config.height = 1280;
		} else if (listRes.equalsIgnoreCase("756x1280")){
			config.width = 756;
			config.height = 1280;
		} else if (listRes.equalsIgnoreCase("1080x1920")){
			config.width = 1080;
			config.height = 1920;
		} else {
			config.width = 1280;
			config.height = 720;
		}

		config.vSyncEnabled = true;
		config.resizable = true;
		config.title = testName;
		config.forceExit = false;
		config.stencil = 0;
		config.depth = 16;
		new LwjglApplication(new S3(test), config);
		return true;
	}

	/**
	 * Runs a libgdx test.
	 * <p>
	 * If no arguments are provided on the command line, shows a list of tests
	 * to choose from. If an argument is present, the test with that name will
	 * immediately be run.
	 *
	 * @param argv command line arguments
	 */
	public static void main(String[] argv) throws Exception{
		if (argv.length > 0){
			if (runTest(argv[0], "1280x720")){
				return;
				// Otherwise, fall back to showing the list
			}
		}
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		new S3libRunTest();
	}
}
