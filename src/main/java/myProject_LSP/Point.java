package myProject_LSP;

import org.springframework.beans.BeanUtils;

import javax.persistence.*;

@Entity
@Table(name="Point_table")
public class Point {

    //수정
    private static int pointQty=10;

    private boolean pointFlowChk=true;
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long orderId;
    private String status;
    private Long sendDate;
    private String pointKind;


    @PostPersist
    public void onPostPersist(){
        System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 26 ");
        System.out.println(this.getStatus());
        if("Point : Point SENDED".equals(this.getStatus())){
            //ORDER -> Point SEND 경우
            PointSended pointSended = new PointSended();
            BeanUtils.copyProperties(this, pointSended);
            pointSended.publishAfterCommit();
        }
    }

    @PrePersist
    public void onPrePersist(){

        System.out.println(this.getStatus() + "++++++++++++++++++++++++++++++++");

        if("ORDER : point SEND".equals(this.getStatus())){
            this.setPointKind("100 point!!");
            this.setStatus("Point : Point SENDED");
            this.setSendDate(System.currentTimeMillis());
        }else {
            this.setStatus("Point : Point SEND CANCELLED");
            //ORDER -> point CANCEL 경우

            System.out.println("47 *****************************************");
            PointSendCancelled pointSendCancelled = new PointSendCancelled();
            BeanUtils.copyProperties(this, pointSendCancelled);
            pointSendCancelled.publishAfterCommit();
        }
    }


    @PreUpdate
    public void onPreUpdate(){
        if("ORDER : point SEND".equals(this.getStatus())){
            this.setPointKind("10 Percent Sale");
            this.setStatus("point : point SENDED");
            this.setSendDate(System.currentTimeMillis());
        }else {
            this.setStatus("point : point SEND CANCELLED");
            //ORDER -> point CANCEL 경우

            PointSendCancelled pointSendCancelled = new PointSendCancelled();
            BeanUtils.copyProperties(this, pointSendCancelled);
            pointSendCancelled.publishAfterCommit();
        }

    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Long getSendDate() {
        return sendDate;
    }

    public void setSendDate(Long sendDate) {
        this.sendDate = sendDate;
    }
    public String getPointKind() {
        return pointKind;
    }

    public void setPointKind(String pointKind) {
        this.pointKind = pointKind;
    }




}
