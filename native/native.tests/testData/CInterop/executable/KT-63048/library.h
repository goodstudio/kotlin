#import "Foundation/NSString.h"
#import "Foundation/NSObject.h"

@interface WithClassProperty : NSObject
-(instancetype) init;
@property (class, readonly, copy) NSString * stringProperty;
@end
