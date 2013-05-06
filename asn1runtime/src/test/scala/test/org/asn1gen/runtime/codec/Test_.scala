package test.org.asn1gen.runtime.codec

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
 
@RunWith(classOf[Suite])
@Suite.SuiteClasses(Array
    ( classOf[TestBerDecoder]
    , classOf[TestBerEncoder]
    , classOf[TestOctetWindow]
    , classOf[TestMyPackratBerDecoder]
    , classOf[TestPackratBerDecoder]
    , classOf[TestSpecs]
    )
)
class Test_ {
}
