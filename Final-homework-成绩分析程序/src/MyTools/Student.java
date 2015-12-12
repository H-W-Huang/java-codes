/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package MyTools;

/**
 *
 * @author H.W
 */
public class Student implements Comparable<Student>{
    
    private String schoolId;
    private String name;
    private int score;

    public Student(String schoolId, String name, int score) {
        this.schoolId = schoolId;
        this.name = name;
        this.score = score;
    }
    
    @Override
    public String toString(){
        String result = schoolId +" "+ name +" "+ score +"";
        return result;
    };
    
    
    @Override 
    public int compareTo(Student another){
        if(this.score > another.score){
            return 1;
        }
        else if(this.score < another.score) return -1;
        else return 0;
    }
    
    /*********getter and settet ***********************/

    /**
     * @return the schoolId
     */
    public String getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId the schoolId to set
     */
    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the score
     */
    public int getScore() {
        return score;
    }

    /**
     * @param score the score to set
     */
    public void setScore(int score) {
        this.score = score;
    }

    /*********getter and settet| END ***********************/
    
}
