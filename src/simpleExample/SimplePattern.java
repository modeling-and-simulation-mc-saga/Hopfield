package simpleExample;

import model.AbstractPatterns;

/**
 *
 * @author tadaki
 */
public class SimplePattern extends AbstractPatterns  {

    public SimplePattern() {
        super(5*5,1);
        
        //記憶させるパターンを
        p[0]= new int[]{
            0,0,1,0,0,
            0,1,0,1,0,
            1,1,1,1,1,
            0,1,0,1,0,
            0,0,1,0,0
        };
    }
    
   
    
}
