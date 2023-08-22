package generator.domain;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import javax.validation.constraints.NotNull;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

/**
* 
* @TableName card
*/
public class Card implements Serializable {

    /**
    * 
    */
    @NotNull(message="[]不能为空")
    @ApiModelProperty("")
    private Integer id;
    /**
    * 名称id
    */
    @ApiModelProperty("名称id")
    private Integer nameId;
    /**
    * 数字
    */
    @Size(max= 2,message="编码长度不能超过2")
    @ApiModelProperty("数字")
    @Length(max= 2,message="编码长度不能超过2")
    private String num;
    /**
    * 花色（1黑桃2红桃3梅花4方片）
    */
    @ApiModelProperty("花色（1黑桃2红桃3梅花4方片）")
    private Integer suit;

    /**
    * 
    */
    private void setId(Integer id){
    this.id = id;
    }

    /**
    * 名称id
    */
    private void setNameId(Integer nameId){
    this.nameId = nameId;
    }

    /**
    * 数字
    */
    private void setNum(String num){
    this.num = num;
    }

    /**
    * 花色（1黑桃2红桃3梅花4方片）
    */
    private void setSuit(Integer suit){
    this.suit = suit;
    }


    /**
    * 
    */
    private Integer getId(){
    return this.id;
    }

    /**
    * 名称id
    */
    private Integer getNameId(){
    return this.nameId;
    }

    /**
    * 数字
    */
    private String getNum(){
    return this.num;
    }

    /**
    * 花色（1黑桃2红桃3梅花4方片）
    */
    private Integer getSuit(){
    return this.suit;
    }

}
