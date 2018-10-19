/**
 * Created by isjo16 on 2018-10-19.
 */
public class Wall extends SimObject{

    /**
     * Operation
     *
     * @param pos - the position the object will start in
     * @return Object
     */
    public Wall(int pos) {
        super(pos);
        this.represent = 'X';
    }

    @Override
    public boolean isSolid() {
        return true;
    }
}
