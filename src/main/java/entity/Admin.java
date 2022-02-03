package entity;

import eceptions.InvalidNationalCodeException;

public class Admin extends User{

    private String nationalCode;

    public Admin(int id, String username, String password, String nationalCode) {
        super(id, username, password);
        validation(nationalCode);
        this.nationalCode = nationalCode;
    }

    public String getNationalCode() {
        return nationalCode;
    }

    public void setNationalCode(String nationalCode) {
        this.nationalCode = nationalCode;
    }

    @Override
    public String toString() {
        return "Admin{" +
                "nationalCode='" + nationalCode + '\'' +
                '}';
    }
    private void validation(String nationalCode){
        if(nationalCode.length()!=10){
            throw new InvalidNationalCodeException("pleas enter national Code in 10 length");
        }
        try {
            long l= Long.valueOf(nationalCode);
        }catch (Exception e){
            throw new InvalidNationalCodeException("pleas enter Numeric vale");
        }
    }
}
