package ca.utoronto.utm.mcs;
import javax.inject.Singleton;
import dagger.Component;
@Singleton
@Component(modules = ReqHandlerModule.class)
public interface ReqHandlerComponent {
    public ReqHandler buildHandler();
}


