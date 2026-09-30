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
	 * A static final image representing the shuffle button.
	 * The image is scaled to 20x20 pixels using Image.SCALE_SMOOTH for smooth scaling.
	 */
	private final Image SHUFFLE_IMAGE = (new ImageIcon(getClass().getResource("/images/shuffle.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

	/**
	 * A static final image representing the loop button.
	 * The image is scaled to 20x20 pixels using Image.SCALE_SMOOTH for smooth scaling.
	 */
	private final Image LOOP_IMAGE = (new ImageIcon(getClass().getResource("/images/loop.png"))).getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);

	/**
	 * A static final image representing the play button.
	 * The image is scaled to 12x12 pixels using Image.SCALE_SMOOTH for smooth scaling.
	 */
	private final Image PLAY_