/**
 * PathwayBrowser.java
 */
import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import kegg.recuperation.Fichiers;
//package kegg.gui;
/**
 * @author 
 */

public class BrowserPathway extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private JLabel image_viewer;
	
	private static String species; // nom de l'espece entree dans le champs species
	private static String map_id;      // nom de l'identifiant entree dans le champs id
	
	private final int OFFSET = 5;
	
	// Initialiser les composants du menu
	private JPanel menu;
	private Box menu_box;
	private Etiquette label_brow;
	private JLabel label_species;
	private static JTextField text_species;
	private JLabel label_ID;
	private static JTextField text_ID;
	protected static JButton menu_bouton_search;
	
		 // Composants zoom
//	private JButton zoom;
//	private JButton unzoom;
	
//	private static final double SCALE = 0.7;
	
	/**
	 * Coordonnees de la souris.
	 */
	private int mouse_x;
	private int mouse_y;
	/**
	 * Identifiant de la reaction selectionnee.
	 */
	private String selectedReaction;
	
    public BrowserPathway(String browser_name, String label_ID_text) {
    	
    	image_viewer = new JLabel();
    	image_viewer.addMouseListener(new MouseListener() {
    	    @Override
    	    public void mouseClicked(MouseEvent me) {
    	    	mouse_x = me.getX();
    	    	mouse_y = me.getY();
    	    	System.out.println("x: "+ mouse_x + "; y: " + mouse_y);
    	    	String reaction = Fichiers.getRectangle(mouse_x, mouse_y, map_id);
    	    	if (reaction != "") {
    	    		selectedReaction = reaction;
    	    		InfoPathway.setReaction(selectedReaction);
    	    		InfoPathway.setInfo(Fichiers.getReactionText(selectedReaction));
    	    		InfoPathway.setList(Fichiers.involvesGenesList(species, map_id));
    	    	}
    	    }
			public void mouseEntered(MouseEvent arg0) {}
			public void mouseExited(MouseEvent arg0) {}
			public void mousePressed(MouseEvent arg0) {}
			public void mouseReleased(MouseEvent arg0) {}
    	});
    	
    	// Menu
		menu = new JPanel();
        menu_box = Box.createHorizontalBox(); // left menu
        
        label_brow = new Etiquette(browser_name);
        label_species = new JLabel("    Species  ");
//        text_species = new JTextField(4);
        text_species = new JTextField("eco", 4);
        
        label_ID = new JLabel("     " + label_ID_text + "  "); 
//        text_ID = new JTextField(5);
        text_ID = new JTextField("00785", 5);
        
        menu_box.add(label_brow);
        menu_box.add(label_species);
        menu_box.add(text_species);
        menu_box.add(label_ID);
        menu_box.add(text_ID);
//        menu_box.add(unzoom);
//        menu_box.add(zoom);
        
        menu_bouton_search = new JButton("Search");

        menu_bouton_search.addActionListener(new ActionListener() {
	    	@Override
	    	public void actionPerformed(ActionEvent e) {
	    		species = text_species.getText().trim();
        		map_id = text_ID.getText().trim();
	        	if (species != "" && map_id != "") {
	        		System.out.println("Species: " + species + " ; MAP_ID: " + map_id); // TEST
//	        			Fichiers.getP(species, map_id);
						afficherContenu(species, map_id);
	        	}
	    	}	
        }); 
        
        menu.setLayout(new BorderLayout());
        menu.add(menu_box, BorderLayout.WEST);
        menu.add(menu_bouton_search, BorderLayout.EAST);
        menu.setBorder(BorderFactory.createEmptyBorder(0,0,OFFSET,0)); // Marge du menu : top, left, bottom, right
    	// fin Menu
        
        // Ajout au browser
	    setLayout(new BorderLayout());
	    add(new JScrollPane(image_viewer), BorderLayout.CENTER);
	    add(menu, BorderLayout.NORTH);
    }
    
	public void afficherContenu(String species, String id) {
		File image_path;
		try {
			image_path = Fichiers.getPathwayFiles(species, id);
			image_viewer.setIcon((Icon)(new ImageIcon(image_path.getPath())));
		} catch (IOException ioe) {
			System.err.println("IOException: " + ioe.getMessage());
		}
	}
    
	
//	public void paintComponent(Graphics g) {
//		super.paintComponent(g);
//		Graphics2D g2d = (Graphics2D) g;
////		g2d.scale(SCALE, SCALE);
//	    if (pathway_image != null)
//		g2d.drawImage(pathway_image, 0, 0, this);
//	    g2d.dispose();
//	}	

	/**
	 * @return species
	 */
    public static String getSpecies() {
    	return species;
    }
    /**
     * @return map_id
     */
    public String getId() {
    	return map_id;
    }
    /**
     * @param species
     */
    public static void setSpecies(String sp) {
    	 species = sp;
    }
    /**
     * @param id
     */
    public static void setId(String id) {
    	 map_id = id;
    }
    /**
     * Set the Species text field.
     * @param species
     */
    public static void setSpeciesText(String species) {
    	setSpecies(species);
    	text_species.setText(species);
    }
    /**
     * Set the Map_ID text field.
     * @param id
     */
    public static void setIdText(String id) {
   	 	setId(id);
   	 	text_ID.setText(id);
    }
    
    public static void setText(String species, String id) {
    	setSpeciesText(species);
    	setIdText(id);
    }
    
    /**
     * Simule un clic sur le bouton search.
     */
    public static void clickSearch() {
    	menu_bouton_search.doClick();
    }
    
    /**
     * @return Retourne la position x de la souris.
     */
    public int getMouseX() {
    	return mouse_x;
    }
    /**
     * @return Retourne la position y de la souris.
     */
    public int getMouseY() {
    	return mouse_y;
    }
}
