package uz.sh.Chapter2_InputAndOutPut;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Predicate;

import java.io.Serializable;

/**
 * Author: Shoxruh Bekpulatov
 * Time: 12/21/22 11:58 AM
 **/
@AllArgsConstructor
@NoArgsConstructor
class Employee implements Serializable {

    static final long serialVersionUID = 42L;

    public String name;

    public int age;

    private transient java.util.Date date;

}
