/*
 * Sudoku GUI with simple solver.
 *
 * The Board and bits representations are uniquely mine.
 * The Pass 2 singleton algorithm comes from:
 * http://www.eddaardvark.co.uk/sudokusolver.html
 *
 * This is my first non-trivial Java program.  I have used code from
 * _Learning_Java_ Third Edition by Patrick Niemeyer & Jonathan Knudsen
 * They sey the code can be used in other programs and documentation.
 *
 * In that spirit, I license this work under the 
 * Creative Commons Attribution 3.0 Unported License.
 * To view a copy of this license, visit:
 * http://creativecommons.org/licenses/by/3.0/ or send a letter to 
 * Creative Commons, 171 Second Street, Suite 300, San Francisco, California, 94105, USA.
 *
 * William D. Cattey
 */


import java.awt.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.BitSet;
import java.util.HashSet;
import java.util.Set;

import javax.swing.*;

public class Sudoku extends JPanel implements ActionListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    GridBagConstraints gbc = new GridBagConstraints();
    JPanel the_grid = new JPanel();
    JTextArea status_pane = new JTextArea ();
    int board [] [] = new int [9] [9] ;
    BitSet [] [] bits = new BitSet [9] [9];
    boolean loaded_flag = false, stuck = false, full_rescan = false, phase_one_complete = false;
    int todo = 0, progress = 0;
    int me_x = 0, me_y = 0;
    Color saved_bg;
    Color my_blue = new Color (200, 200, 255);
    Color my_green = new Color (200, 255, 200);
    Color my_red = new Color (255, 180, 180);
    int debug_level = 0;
    static final boolean DEBUG = false;
    static JFrame the_frame;        // So we can get hold of title and change it.

    public Sudoku() {
        gbc.weightx = 1.0;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        ContainerListener listener = new ContainerAdapter() {
            public void componentAdded(ContainerEvent e) {
                Component comp = e.getChild();
                if (comp instanceof JButton)
                    ((JButton) comp).addActionListener(Sudoku.this);
            }
        };
        addContainerListener(listener);
        JMenu menu = new JMenu("File");
        menu.add(makeMenuItem("Load Puzzle"));
        menu.add(makeMenuItem("Save Puzzle"));
        menu.add(makeMenuItem("Quit"));
        JMenuBar menuBar = new JMenuBar( );
        menuBar.add(menu);
        gbc.gridwidth = 9;
        addGB(this, menuBar, 0, 0);
        // make the top row
        JPanel buttonbar = new JPanel();
        buttonbar.addContainerListener(listener);
        gbc.gridwidth = 1;
        gbc.weightx = 1.0;
        addGB(buttonbar, new JButton("Clear"), 0, 0);
        addGB(buttonbar, new JButton("Status"), 1, 0);
        addGB(buttonbar, new JButton("Phase 1"), 2, 0);
        addGB(buttonbar, new JButton("Phase 2"), 3, 0);
        // addGB(buttonbar, new JButton("Step"), 5, 0);
        // addGB(buttonbar, new JButton("Setup"), 4, 0);
        gbc.gridwidth = 9;
        addGB(this, buttonbar, 0, 1);
        JTextField throw_away = new JTextField();
        saved_bg = throw_away.getBackground();
        for (int tile_y = 0; tile_y < 3; tile_y++) 
            for (int tile_x = 0; tile_x < 3; tile_x++) {
                JPanel tile = new JPanel();
                tile.setBorder(BorderFactory.createRaisedBevelBorder());
                gbc.weightx = 1.0;
                gbc.gridwidth = 1;
                int text_ct = 0;

                for (int i = 0; i < 3; i++) 
                    for (int j = 0; j < 3; j++) {
                        JTextField text = new JTextField(1);
                        if (DEBUG) {
                            text.setText(String.valueOf(text_ct));
                            text_ct++;
                        }
                        text.setInputVerifier( new InputVerifier() {
                            public boolean verify( JComponent comp ) {
                                JTextField field = (JTextField)comp;
                                boolean passed = false;
                                try {
                                    String value = field.getText();
                                    if (value.equals("") )return true; 
                                    int n = Integer.parseInt(value);
                                    if (n == 0) field.setText("");
                                    passed = ( 0 <= n && n <= 9 );
                                } catch (NumberFormatException e) { }
                                if ( !passed ) {
                                    comp.getToolkit().beep();
                                    field.selectAll();
                                }
                                return passed;
                            }
                        } );
                        
                        addGB(tile, text, j, i);
                    }
                addGB(the_grid, tile, tile_x, tile_y);
            }
        addGB(this, the_grid, 0, 2);
        gbc.weighty = 5;
        addGB(this, (new JScrollPane (status_pane)), 0, 3);
    }

    void addGB(Container cont, Component comp, int x, int y) {
        if ((cont.getLayout() instanceof GridBagLayout) == false)
            cont.setLayout(new GridBagLayout());
        gbc.gridx = x;
        gbc.gridy = y;
        cont.add(comp, gbc);
    }

    public void actionPerformed(ActionEvent e) {
        // Button Commands
        if (e.getActionCommand().equals("Setup")) do_setup();
        else if (e.getActionCommand().equals("Clear")) do_clear();
        else if (e.getActionCommand().equals("Status")) do_status();
        else if (e.getActionCommand().equals("Step")) do_step();
        else if (e.getActionCommand().equals("Phase 1")) do_phase_1();
        else if (e.getActionCommand().equals("Phase 2")) do_phase_2();
        // Menu Commands
        else if (e.getActionCommand().equals("Load Puzzle")) load_puzzle( );
        else if (e.getActionCommand().equals("Save Puzzle")) save_puzzle( );
        else if (e.getActionCommand().equals("Quit")) System.exit(0);
    }
    
    private void do_setup ()  {
        todo = 0;
        me_x = 0;
        me_y = 0;
    
        for (int tile_ct = 0; tile_ct < 9; tile_ct++) {
            Component tile = the_grid.getComponent(tile_ct);
            for (int text_ct = 0; text_ct < 9; text_ct++) { 
                Component text = ((Container) tile).getComponent(text_ct);
                int intval, x, y;
    
                x = board_x_from_tile (tile_ct, text_ct);
                y = board_y_from_tile (tile_ct, text_ct);
                
                if (debug_level > 9) {
                    int new_text = text_from_board (x, y);
                    int new_tile = tile_from_board (x, y);
                
                    status_pane.append( String.format("tile: %d, text: %d, x: %d, y: %d, new_tile: %d, new_text: %d\n",
                        tile_ct, text_ct, x, y, new_tile, new_text));
                }
                
                String value = ((JTextField) text).getText();
                if (value.equals("")) {
                    intval = 0;
                    todo++;
                }
                else intval = Integer.parseInt(value);
                board [x] [y] = intval;
                BitSet this_bit = new BitSet(10);
                this_bit.set(intval);
                bits [x] [y] = this_bit;
                
                if (intval > 0) {
                    // ((JTextField) text).setEditable(false);
                    ((JTextField) text).setBackground(my_blue);
                }
            }
        }
        perform_validation ();
        loaded_flag = true;
        stuck = false;
        full_rescan = false;
        phase_one_complete = false;
        progress = todo;    // Initialize progress made.
        if (DEBUG) print_board ();
    }

    private void do_clear() {
        Component [] panels = the_grid.getComponents();
        for (Component c : panels) {
            Component[] texts = ((Container) c).getComponents();
            for (Component t :texts ) {
                ((JTextField) t).setText("");
                // ((JTextField) t).setEditable(true);
                ((JTextField) t).setBackground(saved_bg);
            }
        }
        loaded_flag = false;
    }

    private void do_status() {
        status_pane.append( String.format("todo: %d, me_x: %d, me_y, %d\n", todo, me_x, me_y));
        if (loaded_flag == false) do_setup();
        if (debug_level > 4) print_board();
        print_mays();
    }

    private void do_step() {
        if (loaded_flag == false) do_setup();
        if (stuck) return;
        BitSet me = bits [me_x] [me_y];
        while (me.get(0) == false)  {
            increment_me();     // Skip ones already set and keep going.
            me = bits [me_x] [me_y];
        }
        perform_scan (me);
        if (DEBUG) print_board();
        increment_me ();
    }

    private void do_phase_1() {
        if (loaded_flag == false) do_setup();   // Need this to initialize todo and stuck flags.
        while ((todo > 0) && (stuck != true)) {
            do_step();
        }
        phase_one_complete = true;
    }

    private void do_phase_2() {
        if (loaded_flag == false) do_setup();   // Need this to initialize todo and stuck flags.
        if (phase_one_complete == false) {
            status_pane.append ("Must do Phase 1 first.  Now running Phase 1.\n");
            do_phase_1();
        }
        find_singletons();
        // Reset me_x and me_y so we're ready to step or run.
        me_x = 0;
        me_y = 0;
    }

    private void load_puzzle() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showOpenDialog(this);
        if (result == JFileChooser.CANCEL_OPTION)
            return;
        try {
            File file = chooser.getSelectedFile();
            String my_name = file.getName();
            the_frame.setTitle(my_name);
            FileReader fr = new FileReader (file);
            BufferedReader in = new BufferedReader (fr);
            String line;
            Component [] panels = the_grid.getComponents();
            for (Component c : panels) {
                Component[] texts = ((Container) c).getComponents();
                line = in.readLine();
                if (line == null) throw new IOException ("Premature EOF");
                int i = 0;
                for (Component t :texts ) {
                    String value = new String (line.substring(i,i+1));
                    if (value.equals("0")) value = "";
                    ((JTextField) t).setText(value);
                    // ((JTextField) t).setEditable(true);
                    ((JTextField) t).setBackground(saved_bg);
                    i++;
                }
            }
            loaded_flag = false;
        } catch (Exception e) {
            status_pane.append ("Could not load file: " + e + "\n");
        }
    }

    private void save_puzzle() {
        JFileChooser chooser = new JFileChooser();
        int result = chooser.showSaveDialog(this);
        if (result == JFileChooser.CANCEL_OPTION)
            return;
        try {
            File file = chooser.getSelectedFile();
            FileWriter fw = new FileWriter (file);
            PrintWriter out = new PrintWriter (fw);
            Component [] panels = the_grid.getComponents();
            for (Component c : panels) {
                Component[] texts = ((Container) c).getComponents();
                StringBuilder line = new StringBuilder();
                for (Component t :texts ) {
                    String value = ((JTextField) t).getText();
                    if (value.equals ("")) value = "0";
                    line.append(value);
                    }
                out.println(line);
            }
            fw.close();
        } catch (Exception e) {
            status_pane.append ("Could not save file: " + e + "\n");
        }
    }

    private void flag_conflict(int x, int y) {
        Component tile = the_grid.getComponent(tile_from_board(x, y));
        JTextField text = (JTextField) ((Container) tile)
                .getComponent(text_from_board(x, y));
        text.setBackground(my_red);
    }

    private void set_new_value(BitSet me) {
        int value = me.nextClearBit(0);
        board[me_x][me_y] = value;
        if (debug_level > 7) {
            status_pane.append( String.format("Setting new value : %d, at: x = %d, y = %d, with me = ", 
                    value, me_x, me_y));
            status_pane.append( String.valueOf (me) + "\n");
        }
        me.flip(0, 10);
        Component tile = the_grid.getComponent(tile_from_board(me_x, me_y));
        JTextField text = (JTextField) ((Container) tile)
                .getComponent(text_from_board(me_x, me_y));
        text.setText(String.valueOf(value));
        text.setBackground(my_green);
        // text.setEditable(false);
        todo--;
    }

    private void increment_me (){
        me_x++;
        if (me_x > 8) {
            me_x =0;
            me_y++;
        }
        if (me_y > 8) {
            me_y = 0;
            if (todo == progress) {
                status_pane.append ("Stuck.\n");
                stuck = true;
                full_rescan = true;     // Stop optimizing rescan. Hereafter all algorithms will need a full-rescan.
            } else {
                progress = todo;    // Record our progress.
                status_pane.append ("Loop!\n");
                
            }
        }
    }
    
    /*
     * Scan row, column, tile centered on me_x, me_y.
     * 
     * Modifies bits[me_x][me_y].
     * Can also modify board and text views if set_new_value is called.
     * Signals conflict if cardinality is 10. (No possible setting.)
     */
    
    private void perform_scan (BitSet me) {
        // row
        for (int x = 0; x < 9; x ++) {
            BitSet target = bits [x] [me_y];
            if (target.get(0) == false) me.or(target); // Bit 0 is a constraining value.
        }
        // column
        for (int y = 0; y < 9; y ++) {
            BitSet target = bits [me_x] [y];
            if (target.get(0) == false) me.or(target);
        }
        // tile
        int tile = tile_from_board (me_x, me_y);
        for (int text = 0; text < 9; text ++) {
            BitSet target = bits [board_x_from_tile(tile, text)] [board_y_from_tile (tile, text)];
            if (target.get(0) == false) me.or(target);
        }
        if (me.cardinality() == 9) {
            set_new_value(me);
            perform_rescan ();  // Rescan row, column, tile up to here.  If we recurse, that's ok.
        }
        if (me.cardinality() == 10)
            flag_conflict (me_x, me_y);
        if (debug_level > 2)
            status_pane.append( String.valueOf (me) + "\n");
    }

    /*
     * Rescan the row, column, tile up to me_x, me_y.
     * 
     * Modifies bits[me_x][me_y]
     * Saves and restores me_x, me_y because it fakes out the call to perform_scan.
     */
    private void perform_rescan () {
        int save_me_x = me_x;
        int save_me_y = me_y;
        
        int last_x, last_y, last_text;
        int save_text = text_from_board (save_me_x, save_me_y);
        
        if (full_rescan) {
            last_x = 9;
            last_y = 9;
            last_text = 9;
        } else {
            last_x = save_me_x;
            last_y = save_me_y;
            last_text = save_text;
        }
        
        // Rescan the row at me_y up to me_x or 9 if a full rescan.
        // Scribble on me_x.
        for (int col = 0; col < last_x; col++) {
            if (col == save_me_x) continue;     // In full scan case, don't compare against self.
            me_x = col;
            BitSet me = bits [me_x] [me_y];
            if (me.get(0) == true) perform_scan (me);
        }
        // Rescan the column at me_x up to me_y or 9 if a full rescan.
        me_x = save_me_x;
        for (int row = 0; row < last_y; row ++) {
            if (row == save_me_y) continue;     // In full scan case, don't compare against self.
            me_y = row;
            BitSet me = bits [me_x] [me_y];
            if (me.get(0) == true) perform_scan (me);
        }
        // Rescan the tile holding me, up to me_x, me_y.
        int tile = tile_from_board (save_me_x, save_me_y);
        for (int text = 0; text < last_text; text ++) {
            if (text == save_text) continue;        // In full scan case, don't compare against self.
            me_x = board_x_from_tile (tile, text);
            me_y = board_y_from_tile (tile, text);
            BitSet me = bits [me_x] [me_y];
            if (me.get(0) == true) perform_scan (me);
        }
        // Restore me_x, and me_y.
        me_x = save_me_x;
        me_y = save_me_y;
    }
    
    private boolean set_singleton_value (int x, int y, int number) {
        BitSet me = bits [x] [y];
        if (me.get(0) == false) return false;   // Skip constrained values.
        if (me.get(number) == false) {  // This is the one we want.
            me_x = x;
            me_y = y;
            if (debug_level > 2) {
                status_pane.append( String.format("Setting value %d at: %d, %d.\n", number, x, y));
            }
            me.set(0,10);
            me.clear(number);
            set_new_value(me);
            perform_rescan();           // Propagate the new constraints!
            return true;                    // Signal we're done.
        }
        return false;
    }
    
    
    /*
     * Find Singletons in Rows, Columns, Tiles.
     * 
     * Modifies bits[][], me_x, me_y
     * 
     */
    
    private void find_singletons () {
        int frequency [] = new int [10];    // Waste element zero, but be more comprehensible.
        progress = todo;                    // Take note of our progress.

        // Do Columns.
        for (int col = 0; col < 9; col++) {
            clear_freqs (frequency);
            for (int row = 0; row < 9; row++) {         // Count frequency of unconstrained value possibilities in row.
                BitSet me = bits [col] [row];
                if (me.get(0) == false) continue;   // Skip constrained values.
                for (int number = 1; number < 10; number++) {
                    if (me.get(number) == false) frequency [number]++;  // False means it could be this number.
                }
            }           
            // Any singletons?
            for (int number = 1; number < 10; number++) {
                if (frequency[number] == 1) {       // YES! We have work to do!
                    // We can do multiple numbers here, but only because we're self consistent.
                    // A re-scan is necessary to make sure we don't try and assert a newly constrained value.
                    // Find out which cell in the column has our desired number.
                    for (int row = 0; row < 9; row++) {
                        if (set_singleton_value (col, row, number)) break;
                    }
                }
            }
        }
        // Do Rows.
        for (int row = 0; row < 9; row++) {
            clear_freqs (frequency);
            for (int col = 0; col < 9; col++) {         // Count frequency of unconstrained value possibilities in row.
                BitSet me = bits [col] [row];
                if (debug_level > 3) {
                    status_pane.append( String.format("col: %d, row: %d, me: ", col, row));
                    status_pane.append( String.valueOf (me) + "\n");
                }
                if (me.get(0) == false) continue;   // Skip constrained values.
                for (int number = 1; number < 10; number++) {
                    if (me.get(number) == false) frequency [number]++;  // False means it could be this number.
                }
            }
            // Any singletons?
            for (int number = 1; number < 10; number++) {
                if (frequency[number] == 1) {       // YES! We have work to do!
                    // Find out which cell in the row has our desired number.
                    for (int col = 0; col < 9; col++) {
                        if (set_singleton_value (col, row, number)) break;
                    }
                }
            }
        }
        
        // Do Tiles.
        for (int tile = 0; tile < 9; tile++) {
            clear_freqs (frequency);
            for (int cell = 0; cell < 9; cell++) {          // Count frequency of unconstrained value possibilities in row.
                me_x = board_x_from_tile (tile, cell);
                me_y = board_y_from_tile (tile, cell);
                BitSet me = bits [me_x] [me_y];
                if (debug_level > 3) {
                    status_pane.append( String.format("me_x: %d, me_y: %d, tile: %d, cell: %d, me: ",
                            me_x, me_y, tile, cell));
                    status_pane.append( String.valueOf (me) + "\n");
                }
                if (me.get(0) == false) continue;   // Skip constrained values.
                for (int number = 1; number < 10; number++) {
                    if (me.get(number) == false) frequency [number]++;  // False means it could be this number.
                }
            }           
            // Any singletons?
            for (int number = 1; number < 10; number++) {
                if (frequency[number] == 1) {       // YES! We have work to do!
                    // Find out which cell in the tile has our desired number.
                    for (int cell = 0; cell < 9; cell++) {
                        me_x = board_x_from_tile (tile, cell);
                        me_y = board_y_from_tile (tile, cell);
                        if (set_singleton_value (me_x, me_y, number)) break;
                    }
                }
            }
        }
        if (progress != todo) find_singletons();    // We added new constraints. Tail recurse to rescan.        
    }
    
    private void clear_freqs (int [] frequency) {
        for (int i = 0; i < 10; i++) frequency[i] = 0;
    }
    
    private void assert_singletons (int [] frequency) {
        
    }
    
    private void print_board() {
        for (int y = 0; y < 9; y++) {
            String line = "";
            for (int x = 0; x < 9; x++) {
                // if (board [x] [y] == 0) line = line + '_';
                // else
                line = line + bits[x][y];
            }
            status_pane.append(line + "\n");
        }
        status_pane.append("----\n");
    }

    private void print_mays() {
        for (int y = 0; y < 9; y++) {
            String line = "";
            for (int x = 0; x < 9; x++) {
                BitSet me = bits[x][y];
                BitSet target = (BitSet) me.clone();
                if (target.get(0) == true) target.flip(0,10);
                line = line + target;
            }
            status_pane.append(line +"\n");
        }
        status_pane.append("----\n");
    }

    private int board_x_from_tile(int tile, int text) {
        return text % 3 + tile % 3 * 3;
    }

    private int board_y_from_tile (int tile, int text) {
        return text / 3 + tile / 3 * 3;
    }
    
    private int text_from_board (int x, int y) {
        return x % 3 + y % 3 * 3;
    }
    
    private int tile_from_board (int x, int y) {
        return x / 3 + y / 3 * 3;
    }
    
    private void perform_validation () {
        // For each hand-set element in the board (bit 0 is false)
        // Look at the other hand-set elements of the row, column and tile.
        // Confirm no others match this one.

        for (int x = 0; x < 9; x++) {
            for (int y =0; y < 9; y++) {
                BitSet me = bits [x][y];
                if (me.get(0) == false) {   // A bound value look scan neighbors                    
                    // Scan the row.
                    for (int col = 0; col < 9; col++) {
                        if (col == y) continue; // Do not compare ourself with ourself.
                        BitSet target = bits [x] [col];
                        if (me.equals(target)) flag_conflict (x,y);
                    }
                    // Scan the column.
                    for (int row = 0; row < 9; row ++) {
                        if (row == x) continue; // Do not compare ourself with ourself.
                        BitSet target = bits [row] [y];
                        if (me.equals(target)) flag_conflict (x,y);
                    }
                    // Scan the tile.
                    int tile = tile_from_board (x, y);
                    for (int text = 0; text < 9; text ++) {
                        int tx = board_x_from_tile (tile, text);
                        int ty = board_y_from_tile (tile, text);
                        if ((tx == x) && (ty == y)) continue;
                        BitSet target = bits [tx] [ty];
                        if (me.equals(target)) flag_conflict (x,y);
                    }
                }
            }
        }
    }
    
    private JMenuItem makeMenuItem(String name) {
        JMenuItem m = new JMenuItem(name);
        m.addActionListener(this);
        return m;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO Auto-generated method stub
        the_frame = new JFrame("Sudoku Sheet");

        // Set our focus traversal keys
        Set<AWTKeyStroke> old = the_frame.getFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS);
        HashSet<AWTKeyStroke> keys = new HashSet<AWTKeyStroke> (old);
        AWTKeyStroke ks = AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_RIGHT, 0, true);
        keys.add(ks);
        the_frame.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, keys);
        // Now the backward traversal keys
        old = the_frame.getFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS);
        keys = new HashSet<AWTKeyStroke> (old);
        ks = AWTKeyStroke.getAWTKeyStroke(KeyEvent.VK_LEFT, 0, true);
        keys.add(ks);
        the_frame.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, keys);
        
        the_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        the_frame.setSize(400, 450);
        the_frame.setLocation(200, 200);
        the_frame.setContentPane(new Sudoku());
        the_frame.setVisible(true);
    }
}