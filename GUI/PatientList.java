import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class PatientList extends JPanel {

    public ArrayList<PatientListItem> pli = new ArrayList<>();

    public PatientList(int n, String[] name, String[] year) {
        super();

        Container table = new Container();
        table.setLayout(new BoxLayout(table, BoxLayout.Y_AXIS));

        for (int i = 0; i < n; i++) {
            PatientListItem pli_cur = new PatientListItem(name[i], year[i]);
            pli.add(pli_cur);
            table.add(pli_cur);
        }

        JScrollPane sp = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        this.add(sp);
    }
}
