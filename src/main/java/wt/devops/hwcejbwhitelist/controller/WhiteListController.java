package wt.devops.hwcejbwhitelist.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;
import wt.devops.hwcejbwhitelist.service.WhitelistService;

import javax.annotation.Resource;

/**
 * Description
 *
 * @author uname.chen
 * @date 19-2-20
 */
@RestController
@RequestMapping(value="/huaweicloud/ejb/whitelist")
public class WhiteListController {

    private static final Logger logger = LoggerFactory.getLogger(WhiteListController.class);

    @Resource
    private WhitelistService whitelistService;

    @ApiOperation(value="启用EJB白名单", notes="启用, 并更新Whitelist")
    @ApiImplicitParam(name = "context", value = "Huaweicloud", required = true, dataType = "HuaweicloudContext")
    @PutMapping(value="/switch-on")
    public void switchOnWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("启用EJB白名单:{}", context.getWhitelist());

        boolean isUpdateSwitch = Boolean.TRUE;
        boolean isSwitchOn = Boolean.TRUE;
        whitelistService.updateWhitelist(context, isUpdateSwitch, isSwitchOn);
    }


    @ApiOperation(value="停用EJB白名单", notes="停用, 并更新Whitelist")
    @ApiImplicitParam(name = "context", value = "Huaweicloud", required = true, dataType = "HuaweicloudContext")
    @PutMapping(value="/switch-off")
    public void switchOffWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("停用EJB白名单:{}", context.getWhitelist());

        boolean isUpdateSwitch = Boolean.TRUE;
        boolean isSwitchOn = Boolean.FALSE;
        whitelistService.updateWhitelist(context, isUpdateSwitch, isSwitchOn);
    }


    @ApiOperation(value="更新EJB白名单", notes="更新 Whitelist, 不更新开关状态")
    @ApiImplicitParam(name = "context", value = "Huaweicloud", required = true, dataType = "HuaweicloudContext")
    @PostMapping(value="")
    public void uploadWhitelist(@RequestBody HuaweicloudContext context){

        logger.info("上传EJB白名单:{}", context.getWhitelist());

        boolean isUpdateSwitch = Boolean.FALSE;
        boolean isSwitchOn = Boolean.FALSE;
        whitelistService.updateWhitelist(context, isUpdateSwitch, isSwitchOn);
    }


}