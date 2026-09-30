```java
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.swing.*;
import javax.swing.plaf.basic.BasicSliderUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@SuppressWarnings("serial")
public class AudioPlayer extends JPanel implements ActionListener
{
	/**
	 * Image representing the shuffle icon.
	 */
	private final Image SHUFFLE_IMAGE = (new ImageIcon(getClass().getResource("/images/shuffle.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	/**
	 * Image representing the loop icon.
	 */
	private final Image LOOP_IMAGE = (new ImageIcon(getClass().getResource("/images/loop.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
	/**
	 * Image representing the play button.
	 */
	private final Image PLAY_IMAGE = (new ImageIcon(getClass().getResource("/images/play_button.png"))).getImage().getScaledInstance(12, 12, Image.SCALE_SMOOTH);
	/**
	 * Image representing the pause button.
	 */
	private final Image PAUSE_IMAGE = (new ImageIcon(getClass().getResource("/images/pause_button.