#include <stdio.h>
#include <assert.h>
#include <string.h>
#include "svc.h"
#include "testcases.h"

int test_print_commit_3() {
    // 写入hello.py
    // hash=2027
    FILE *fp = fopen("hello.py", "w");
    fprintf(fp, "print(\"Hello\")\n");
    fclose(fp);
    // 写入view.sh
    // hash=4871
    fp = fopen("view.sh", "w");
    fprintf(fp, "#!/usr/bin/env bash\n");
    fprintf(fp, "\n");
    fprintf(fp, "hugo --i18n-warnings server\n");
    fclose(fp);
    // 写入mc-config.txt
    // hash=10405
    fp = fopen("mc-config.txt", "w");
    fprintf(fp, "launchRodAngle,0,0\n");
    fprintf(fp, "totalMass,1,0.2\n");
    fprintf(fp, "cog,0,0\n");
    fprintf(fp, "cop,0,0\n");
    fprintf(fp, "temperature,0,0\n");
    fprintf(fp, "pressure,0,0\n");
    fprintf(fp, "nominalWindSpeed,0,0\n");
    fprintf(fp, "surfaceFinish,0,0\n");
    fprintf(fp, "cd,0,0");
    fclose(fp);


    void *helper = svc_init();
    int temp_int;
    char *temp_str, *tmp_str_result;

    // 1)add hello.py
    temp_str = deepcopy_str("hello.py");
    svc_add(helper, temp_str);
    temp_int=hash_file(helper, temp_str);
    assert(temp_int == 2027);
    free(temp_str);

    // 2)add view.sh
    temp_str = deepcopy_str("view.sh");
    svc_add(helper, temp_str);
    temp_int = hash_file(helper, temp_str);
    assert(temp_int == 4871);
    free(temp_str);

    // 3)commit
    temp_str = deepcopy_str("Initial commit");
    tmp_str_result = svc_commit(helper, temp_str);
    assert(tmp_str_result!=NULL);
    assert(strcmp(tmp_str_result, "2e64a9") == 0);
    free(temp_str);

    // 4)branch
    temp_str = deepcopy_str("fixes");
    temp_int = svc_branch(helper, temp_str);
    assert(temp_int==0);
    free(temp_str);
//    assert(temp_int == 0);

    // 5)checkout
    temp_str = deepcopy_str("fixes");
    svc_checkout(helper, temp_str);
    free(temp_str);
//    assert(temp_int == 0);

    // 6)add mc-config.txt
    temp_str = deepcopy_str("mc-config.txt");
    svc_add(helper, temp_str);
    temp_int =hash_file(helper, temp_str);
    assert(temp_int == 10405);
    free(temp_str);

    // 6-2) 删除view.sh
    remove("view.sh");

    // 7)commit "Some changes"
    temp_str = deepcopy_str("Some changes");
    tmp_str_result = svc_commit(helper, temp_str);
    assert(tmp_str_result!=NULL);
    assert(strcmp(tmp_str_result, "993564") == 0);
    free(temp_str);

    // 8)checkout
    temp_str = deepcopy_str("master");
    svc_checkout(helper, temp_str);
    free(temp_str);

    // 11)merge
    temp_str = deepcopy_str("fixes");
    svc_merge(helper, temp_str, NULL, 0);
    free(temp_str);

    // 12) get_commit
    void *commit = get_commit(helper, "2e64a9");
    assert(commit!=NULL);

    // 13) print_commit
    print_commit(helper, "2e64a9");

    // 14) get_commit
    commit = get_commit(helper, "993564");
    assert(commit!=NULL);

    // 15) print_commit
    print_commit(helper, "993564");






    cleanup(helper);
    return 1;
}