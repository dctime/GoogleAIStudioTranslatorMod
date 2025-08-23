package net.github.dctime.compability.jade;


import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaCommonRegistration;
import snownee.jade.api.IWailaPlugin;

@snownee.jade.api.WailaPlugin
public class WailaPlugin implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        //TODO register data providers
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
//        registration.addBeforeRenderCallback(new TestBeforeRenderCallback());
        registration.addTooltipCollectedCallback(new TestTooltipCollectedCallback());
    }
}
