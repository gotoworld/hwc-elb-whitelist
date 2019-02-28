package wt.devops.hwcelbwhitelist.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import wt.devops.hwcelbwhitelist.pojo.ResponseVO;
import wt.devops.hwcelbwhitelist.pojo.WhitelistVO;
import wt.devops.hwcelbwhitelist.service.WhitelistService;
import wt.devops.hwcelbwhitelist.utils.JacksonUtil;

import javax.annotation.Resource;
import java.util.List;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-20
 */
@RestController
@RequestMapping(value="/huaweicloud/elb/whitelist")
public class WhiteListController {

    private static final Logger logger = LoggerFactory.getLogger(WhiteListController.class);

    @Resource
    private WhitelistService whitelistService;

    @ApiOperation(value="启用ELB白名单", notes="启用, 并更新Whitelist")
    @ApiImplicitParam(name = "context", value = "context", required = true, dataType = "HuaweicloudContext")
    @PutMapping(value="/switch-on")
    public void switchOnWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("启用ELB白名单:{}", context.getWhitelist());

        boolean isUpdateSwitch = Boolean.TRUE;
        boolean isSwitchOn = Boolean.TRUE;
        whitelistService.updateWhitelist(context, isUpdateSwitch, isSwitchOn);
    }


    @ApiOperation(value="停用ELB白名单", notes="停用, 并更新Whitelist")
    @ApiImplicitParam(name = "context", value = "context", required = true, dataType = "HuaweicloudContext")
    @PutMapping(value="/switch-off")
    public void switchOffWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("停用ELB白名单:{}", context.getWhitelist());

        boolean isUpdateSwitch = Boolean.TRUE;
        boolean isSwitchOn = Boolean.FALSE;
        whitelistService.updateWhitelist(context, isUpdateSwitch, isSwitchOn);
    }


    @ApiOperation(value="更新ELB白名单", notes="更新 Whitelist, 不更新开关状态")
    @ApiImplicitParam(name = "context", value = "context", required = true, dataType = "HuaweicloudContext")
    @PostMapping(value="")
    public void uploadWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("上传ELB白名单:{}", context.getWhitelist());

        boolean isUpdateSwitch = Boolean.FALSE;
        boolean isSwitchOn = Boolean.FALSE;
        whitelistService.updateWhitelist(context, isUpdateSwitch, isSwitchOn);
    }



    @ApiOperation(value="获取 Whitelist 列表", notes="获取 Whitelist 列表")
    @ApiImplicitParam(name = "context", value = "context", required = true, dataType = "HuaweicloudContext")
    @PostMapping(value="list")
    @ResponseBody
    public ResponseVO<WhitelistVO> listWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("获取 Whitelist 列表:{}", context.getWhitelist());

        List<WhitelistVO> theList =  whitelistService.getWhitelist(context);

        return new ResponseVO("0","success",theList == null? 0 : theList.size(),theList);
    }


    @ApiOperation(value="启用, 并更新ELB白名单", notes="启用, 并更新Whitelist")
    @ApiImplicitParam(name = "context", value = "context", required = true, dataType = "HuaweicloudContext")
    @PutMapping(value="/list-switchon-withupdate")
    public void switchOnAndUpdateSelectedWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("启用ELB白名单:{}", context.getWhitelist());
        logger.info("WhitelistVoList:{}", JacksonUtil.objectToJsonWithPrettyFormat(context.getWhitelistVoList()));

        boolean isUpdateSwitch = Boolean.TRUE;
        boolean isSwitchOn = Boolean.TRUE;
        boolean isUpdateWhiteList = Boolean.TRUE;
        whitelistService.updateSelectWhitelist(context, isUpdateSwitch, isSwitchOn, isUpdateWhiteList);
    }


    @ApiOperation(value="启用ELB白名单", notes="启用Whitelist")
    @ApiImplicitParam(name = "context", value = "context", required = true, dataType = "HuaweicloudContext")
    @PutMapping(value="/list-switchon")
    public void switchOnSelectedWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("启用ELB白名单:{}", context.getWhitelist());
        logger.info("WhitelistVoList:{}", JacksonUtil.objectToJsonWithPrettyFormat(context.getWhitelistVoList()));

        boolean isUpdateSwitch = Boolean.TRUE;
        boolean isSwitchOn = Boolean.TRUE;
        boolean isUpdateWhiteList = Boolean.FALSE;
        whitelistService.updateSelectWhitelist(context, isUpdateSwitch, isSwitchOn, isUpdateWhiteList);
    }


    @ApiOperation(value="停用ELB白名单", notes="停用Whitelist")
    @ApiImplicitParam(name = "context", value = "context", required = true, dataType = "HuaweicloudContext")
    @PutMapping(value="/list-switchoff")
    public void switchOffSelectedWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("停用ELB白名单:{}", context.getWhitelist());
        logger.info("WhitelistVoList:{}", JacksonUtil.objectToJsonWithPrettyFormat(context.getWhitelistVoList()));

        boolean isUpdateSwitch = Boolean.TRUE;
        boolean isSwitchOn = Boolean.FALSE;
        boolean isUpdateWhiteList = Boolean.FALSE;
        whitelistService.updateSelectWhitelist(context, isUpdateSwitch, isSwitchOn, isUpdateWhiteList);
    }


    @ApiOperation(value="上传ELB白名单", notes="上传Whitelist")
    @ApiImplicitParam(name = "context", value = "context", required = true, dataType = "HuaweicloudContext")
    @PutMapping(value="/list-upload")
    public void uploadSelectedWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("上传ELB白名单:{}", context.getWhitelist());
        logger.info("WhitelistVoList:{}", JacksonUtil.objectToJsonWithPrettyFormat(context.getWhitelistVoList()));

        boolean isUpdateSwitch = Boolean.FALSE;
        boolean isSwitchOn = Boolean.FALSE;
        boolean isUpdateWhiteList = Boolean.TRUE;
        whitelistService.updateSelectWhitelist(context, isUpdateSwitch, isSwitchOn, isUpdateWhiteList);
    }
}