package app.utils;

public class SwingUtils {
    // See: https://stackoverflow.com/questions/40775607/java-set-get-all-components-text-in-a-jframe
    public static void setSwingObjectText(Object object, String text) {
        //generated
        if (object instanceof javax.swing.AbstractButton)
            ((javax.swing.AbstractButton) object).setText(text);
        else if (object instanceof javax.swing.DefaultListCellRenderer)
            ((javax.swing.DefaultListCellRenderer) object).setText(text);
        else if (object instanceof javax.swing.JButton)
            ((javax.swing.JButton) object).setText(text);
        else if (object instanceof javax.swing.JCheckBox)
            ((javax.swing.JCheckBox) object).setText(text);
        else if (object instanceof javax.swing.JCheckBoxMenuItem)
            ((javax.swing.JCheckBoxMenuItem) object).setText(text);
        else if (object instanceof javax.swing.JEditorPane)
            ((javax.swing.JEditorPane) object).setText(text);
        else if (object instanceof javax.swing.JFormattedTextField)
            ((javax.swing.JFormattedTextField) object).setText(text);
        else if (object instanceof javax.swing.JLabel)
            ((javax.swing.JLabel) object).setText(text);
        else if (object instanceof javax.swing.JMenu)
            ((javax.swing.JMenu) object).setText(text);
        else if (object instanceof javax.swing.JMenuItem)
            ((javax.swing.JMenuItem) object).setText(text);
        else if (object instanceof javax.swing.JPasswordField)
            ((javax.swing.JPasswordField) object).setText(text);
        else if (object instanceof javax.swing.JRadioButton)
            ((javax.swing.JRadioButton) object).setText(text);
        else if (object instanceof javax.swing.JRadioButtonMenuItem)
            ((javax.swing.JRadioButtonMenuItem) object).setText(text);
        else if (object instanceof javax.swing.JTextArea)
            ((javax.swing.JTextArea) object).setText(text);
        else if (object instanceof javax.swing.JTextField)
            ((javax.swing.JTextField) object).setText(text);
        else if (object instanceof javax.swing.JTextPane)
            ((javax.swing.JTextPane) object).setText(text);
        else if (object instanceof javax.swing.JToggleButton)
            ((javax.swing.JToggleButton) object).setText(text);
        else if (object instanceof javax.swing.plaf.basic.BasicArrowButton)
            ((javax.swing.plaf.basic.BasicArrowButton) object).setText(text);
        else if (object instanceof javax.swing.plaf.basic.BasicComboBoxRenderer)
            ((javax.swing.plaf.basic.BasicComboBoxRenderer) object).setText(text);
        else if (object instanceof javax.swing.plaf.metal.MetalComboBoxButton)
            ((javax.swing.plaf.metal.MetalComboBoxButton) object).setText(text);
        else if (object instanceof javax.swing.plaf.metal.MetalScrollButton)
            ((javax.swing.plaf.metal.MetalScrollButton) object).setText(text);
        else if (object instanceof javax.swing.table.DefaultTableCellRenderer)
            ((javax.swing.table.DefaultTableCellRenderer) object).setText(text);
        else if (object instanceof javax.swing.text.JTextComponent)
            ((javax.swing.text.JTextComponent) object).setText(text);
        else if (object instanceof javax.swing.tree.DefaultTreeCellRenderer)
            ((javax.swing.tree.DefaultTreeCellRenderer) object).setText(text);
        else if (object instanceof javax.swing.JDialog)
            ((javax.swing.JDialog) object).setTitle(text);
        else if (object instanceof javax.swing.JFrame)
            ((javax.swing.JFrame) object).setTitle(text);
        else if (object instanceof javax.swing.JInternalFrame)
            ((javax.swing.JInternalFrame) object).setTitle(text);
        else if (object instanceof javax.swing.border.TitledBorder)
            ((javax.swing.border.TitledBorder) object).setTitle(text);
    }
}
